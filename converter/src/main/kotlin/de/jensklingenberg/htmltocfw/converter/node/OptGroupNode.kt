package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Node

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.OptGroup]
 */
class OptGroupNode(private val htmlAttributes: Attributes, val childNodes: List<Node>) : MyNode {

    val ATTR_LABEL = "label"
    val TAG = "OptGroup"
    override fun toString(): String {

        var str = "$TAG ("
        val hasSrc = htmlAttributes.get(ATTR_LABEL)
        htmlAttributes.remove(ATTR_LABEL)

        val attrText = parseAttributes(htmlAttributes.asList())
        str += (attrText)

        if (attrText.isNotBlank()) {
            str += (",")
        }
        str += "label = \"${hasSrc}\"" + ")"

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
        str += "}\n"
        return str
    }

}