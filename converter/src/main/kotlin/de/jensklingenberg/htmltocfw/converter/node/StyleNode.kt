package de.jensklingenberg.htmltocfw.converter.node

import com.helger.commons.collection.impl.ICommonsList
import com.helger.css.ECSSVersion
import com.helger.css.decl.CSSStyleRule
import com.helger.css.reader.CSSReader
import de.jensklingenberg.htmltocfw.converter.getStyleProperties
import org.jsoup.nodes.Element
import java.nio.charset.StandardCharsets

/**
 *
 */
fun parseStyleRules(allStyleRules: ICommonsList<CSSStyleRule>): String {
    var str = ""
    allStyleRules.forEach { styleRule ->
        val styleName = styleRule.allSelectors.joinToString { it.asCSSString }
        str += "\"$styleName\" style { \n"
        styleRule.allDeclarations.forEach { declaration ->
            val propName = declaration.property
            val propValue = declaration.expressionAsCSSString

            str += getStyleProperties(propName, propValue)
            str += "\n"
        }
        str += "}\n"
    }
    return str
}

/**
 * This will transform the style tag to a StyleSheet
 */
class StyleNode(private val element: Element) : MyNode {

    override fun toString(): String {
        var str = "fun appStylesheet() = object : StyleSheet() {\n" + "init {\n"
        val aCSS = CSSReader.readFromString(element.data(), StandardCharsets.UTF_8, ECSSVersion.CSS30);

        aCSS?.allStyleRules?.let {
            str += parseStyleRules(it)
        }

        aCSS?.allMediaRules?.forEach { mediaRule ->
            val media = mediaRule.allMediaQueries.joinToString { it.asCSSString }
            mediaRule?.allStyleRules?.let {
                str += "media(\"$media\") {\n" + parseStyleRules(it) + "}\n"
            }
        }
        str += "}}\n"
        return str
    }
}