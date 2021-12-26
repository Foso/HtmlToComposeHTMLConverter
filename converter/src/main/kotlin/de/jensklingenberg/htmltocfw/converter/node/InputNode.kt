package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Input]
 */
class InputNode(private val attributes: Attributes) : MyNode {
    val ATTR_TYPE = "type"
    val TAG = "Input"

    override fun print(): String {
        var str = "$TAG ("
        val hasType = attributes.get(ATTR_TYPE)
        attributes.remove(ATTR_TYPE)
        val attrText = parseAttributes(attributes.asList())
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