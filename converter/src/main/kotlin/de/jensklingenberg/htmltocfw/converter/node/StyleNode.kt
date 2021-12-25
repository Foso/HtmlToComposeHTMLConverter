package de.jensklingenberg.htmltocfw.converter.node

import com.helger.commons.collection.impl.ICommonsList
import com.helger.css.ECSSVersion
import com.helger.css.decl.CSSStyleRule
import com.helger.css.reader.CSSReader
import de.jensklingenberg.htmltocfw.converter.getStyleProperties
import org.jsoup.nodes.Element
import java.nio.charset.StandardCharsets


fun parseStyleNodes(allStyleRules: ICommonsList<CSSStyleRule>?): String {
    var str = ""
    allStyleRules?.forEach {
        val styleName = it.allSelectors.joinToString { it.asCSSString }
        str += "\"$styleName\" style { \n"
        it.allDeclarations.forEach {
            val propName = it.property
            val propValue = it.expressionAsCSSString

            str += getStyleProperties(propName,propValue)
            str += "\n"
        }
        str += "}\n"
    }
    return str
}

class StyleNode(private val element: Element) : MyNode {

    override fun print(): String {
        var str = "object AppStylesheet : StyleSheet() {\n"
        str += "init {\n"
        val aCSS = CSSReader.readFromString(element.data(), StandardCharsets.UTF_8, ECSSVersion.CSS30);

        str += parseStyleNodes(aCSS?.allStyleRules)

        aCSS?.allMediaRules?.forEach {
            val media = it.allMediaQueries.first?.asCSSString

            str += "media(\"$media\") {\n" + parseStyleNodes(it?.allStyleRules) + "}\n"
            it
        }
        str += "}}\n"
        return str
    }
}