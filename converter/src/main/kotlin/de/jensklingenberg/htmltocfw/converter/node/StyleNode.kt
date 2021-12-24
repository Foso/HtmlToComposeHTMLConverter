package de.jensklingenberg.htmltocfw.converter.node

import com.helger.css.ECSSVersion
import com.helger.css.reader.CSSReader
import de.jensklingenberg.htmltocfw.converter.CFWAttributeNames
import org.jsoup.nodes.Element
import java.nio.charset.StandardCharsets

class StyleNode(private val element: Element) : MyNode {

    override fun print(): String {
        var str = "object AppStylesheet : StyleSheet() {\n"
        str += "init {\n"
        val aCSS = CSSReader.readFromString(element.data(), StandardCharsets.UTF_8, ECSSVersion.CSS30);

        aCSS?.allStyleRules?.forEach {
            var styleName = it.allSelectors[0].allMembers[0].asCSSString
            str += "\"$styleName\" style { "
            it.allDeclarations.forEach {
            val propName = it.property
                str += when (val test = CFWAttributeNames.values().find { it.htmlAttrName == propName }) {
                    CFWAttributeNames.TEXTDECORATION, CFWAttributeNames.TEXTALIGN, CFWAttributeNames.BACKGROUNDIMAGE, CFWAttributeNames.FONTFAMILY, CFWAttributeNames.FONT -> {
                        "${test.cfwAttrName}(\"${it.expressionAsCSSString}\")"
                    }
                    else->{
                         "property(\"" + it.property + "\",\"" + it.expressionAsCSSString + "\")"
                    }
                }
                str += "\n"
            }
            str += "}\n"
        }

        str += "}}\n"



        return str
    }
}