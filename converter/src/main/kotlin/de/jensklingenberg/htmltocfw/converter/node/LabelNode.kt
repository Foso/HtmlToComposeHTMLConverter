package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Node

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Label]
 */

class LabelNode(private val htmlAttributes: Attributes, private val childNodes: List<Node>) : MyNode {
    private val ATTR_FOR = "for"
    private val TAG = "Label"

    override fun toString(): String {
        var str = "$TAG ("
        val forValue = htmlAttributes.get(ATTR_FOR)
        htmlAttributes.remove(ATTR_FOR)

        val attrText = parseAttributes(htmlAttributes.asList())
        str += attrText

        if (forValue.isNotBlank()) {
            if (attrText.isNotBlank()) {
                str += (", ")
            }
        }
        str += ("forId = \"${forValue}\"") + ")"

        val childNodesText = childNodes.joinToString("") {
            getMyNode(it).toString()
        }

        str += "{"
        if (childNodesText.isNotBlank()) {
            str += "\n" + childNodesText
        }

        str += "}\n"
        return str
    }

}