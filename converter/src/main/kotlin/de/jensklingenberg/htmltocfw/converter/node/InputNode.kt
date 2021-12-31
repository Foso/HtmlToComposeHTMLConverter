package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Input]
 */
class InputNode(private val htmlAttributes: Attributes) : MyNode {
    private val ATTR_TYPE = "type"
    private val TAG = "Input"

    override fun toString(): String {
        var str = "$TAG ("
        val typeValue = htmlAttributes.get(ATTR_TYPE)
        htmlAttributes.remove(ATTR_TYPE)
        val attrText = parseAttributes(htmlAttributes.asList())
        str += attrText

        if (typeValue.isNotBlank()) {
            if (attrText.isNotBlank()) {
                str += (", ")
            }
            val type = typeValue.capitalize()
            str += ("type = InputType.${type}")
        }

        str += ")\n"
        return (str)
    }

}