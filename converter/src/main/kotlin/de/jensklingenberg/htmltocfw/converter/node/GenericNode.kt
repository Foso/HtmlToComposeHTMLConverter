package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import de.jensklingenberg.htmltocfw.converter.visitor.Visitor
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node

class GenericNode(private val tag: String, val childNodes: List<Node>, val attrs: List<ComposeAttribute>) : MyNode {

    override fun toString(): String {
        var str = tag.capitalize() + " "


        val attrs = parseAttributes(attrs)
        if (this.attrs.isNotEmpty()) {
            str += "($attrs)"
        }

        str += "{"
        val childNodesText = childNodes.joinToString("") {
            getMyNode(it).toString()
        }
        if (childNodesText.isNotBlank()) {
            str += "\n" + childNodesText + "\n"
        }
        str += "}\n"
        return str
    }

    override fun accept(visitor: Visitor) {
        visitor.visitGeneric(this)
    }

    companion object {
        fun createGenericNode(node: Node): GenericNode {
            val tagName = (node as Element).tagName()
            val attrs = node.attributes().map { ComposeAttribute(it.key, it.value) }
            return GenericNode(tagName, node.childNodes(), attrs)
        }
    }
}