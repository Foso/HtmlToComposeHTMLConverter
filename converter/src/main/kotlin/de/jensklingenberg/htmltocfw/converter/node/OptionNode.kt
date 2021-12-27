package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Node

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Option]
 */
class OptionNode(private val attributes: Attributes, val childNodes: List<Node>) : MyNode {
    val ATTR_VALUE = "value"
    val TAG = "Option"

    override fun toString(): String {

        var str = "$TAG ("
        val valueValue = attributes.get(ATTR_VALUE)
        attributes.remove(ATTR_VALUE)

        val attrText = parseAttributes(attributes.asList())
        str += attrText

        if (attrText.isNotBlank()) {
            str += (",")
        }
        str += ("value = \"${valueValue}\"") + ")"

        val childNodesText = childNodes.joinToString(separator = "") {
            getMyNode(it).toString()
        }

        str += "{"
        if (childNodesText.isNotBlank()) {
            str += "\n"
        }
        str += childNodesText
        if (childNodesText.isNotBlank()) {
            str += "\n"
        }
        str += ("}\n")
        return str
    }

}