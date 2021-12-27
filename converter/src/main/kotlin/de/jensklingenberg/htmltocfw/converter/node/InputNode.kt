package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Input]
 */
class InputNode(private val htmlAttributes: Attributes) : MyNode {
    val ATTR_TYPE = "type"
    val TAG = "Input"

    override fun toString(): String {
        var str = "$TAG ("
        val hasType = htmlAttributes.get(ATTR_TYPE)
        htmlAttributes.remove(ATTR_TYPE)
        val attrText = parseAttributes(htmlAttributes.asList())
        str += attrText

        if (hasType.isNotBlank()) {
            if (attrText.isNotBlank()) {
                str += (", ")
            }
            val type = hasType.capitalize()
            str += ("type = InputType.${type}")
        }

        str += ")\n"
        return (str)
    }

}