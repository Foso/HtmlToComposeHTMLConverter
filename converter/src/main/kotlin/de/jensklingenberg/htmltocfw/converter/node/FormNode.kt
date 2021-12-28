package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Node

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Form]
 */
class FormNode(private val htmlAttributes: Attributes, private val childNodes: List<Node>) : MyNode {
    private val TAG = "Form"
    private val ATTR_ACTION = "action"

    override fun toString(): String {
        var str = "$TAG ("
        val actionValue = htmlAttributes.get(ATTR_ACTION)
        htmlAttributes.remove(ATTR_ACTION)

        val attrText = parseAttributes(htmlAttributes.asList())
        str += attrText

        if (actionValue.isNotBlank() && attrText.isNotBlank()) {
            str += (", ")
        }
        str += "action = \"${actionValue}\"" + ")"

        val childNodesText = childNodes.joinToString("") { node ->
            getMyNode(node).toString()
        }

        str += "{"

        if (childNodesText.isNotBlank()) {
            str += "\n" + childNodesText + "\n"
        }

        str += ("}\n")
        return str
    }

}