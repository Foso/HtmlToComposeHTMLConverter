package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Node

class GenericNode(private val tag: String, val childNodes: List<Node>, val attributes: Attributes) : MyNode {

    override fun toString(): String {
        var str = tag.capitalize() + " "

        val attrs = parseAttributes(attributes.asList())
        if (attrs.isNotBlank()) {
            str += "($attrs)"
        }

        val childNodesText = childNodes.joinToString("") {
            getMyNode(it).toString()
        }

        str += "{"

        if (childNodesText.isNotBlank()) {
            str += "\n" + childNodesText + "\n"
        }
        str += ("}\n")
        return str
    }

    override fun accept(visitor: Visitor) {
        visitor.visitGeneric(this)
    }
}