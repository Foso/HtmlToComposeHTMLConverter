package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Node

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.A]
 */
class ANode(private val htmlAttributes: Attributes, val childNodes: List<Node>) : MyNode {
    private val TAG = "A"
    private val ATTR_HREF = "href"

    override fun toString(): String {

        var str = "$TAG ("
        val hrefValue = htmlAttributes.get(ATTR_HREF)
        htmlAttributes.remove(ATTR_HREF)

        val attrText = parseAttributes(htmlAttributes.asList())
        str += attrText

        if (hrefValue.isNotBlank()) {
            if (attrText.isNotBlank()) {
                str += (", ")
            }
        }
        str += ("href = \"${hrefValue}\"") + ")"

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