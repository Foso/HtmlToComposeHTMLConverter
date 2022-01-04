package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import de.jensklingenberg.htmltocfw.converter.visitor.Visitor
import org.jsoup.nodes.Node

class PathNode(private val attrs: List<ComposeAttribute>, val dValue: String) : MyNode {

    private val TAG = "Path"
    override fun accept(visitor: Visitor) {
        visitor.visitPath(this)
    }

    override fun toString(): String {
        var str = "$TAG ("

        val arguments = mutableListOf<String>()

        if (attrs.isNotEmpty()) {
            arguments.add(parseAttributes(attrs))
        }

        if (dValue.isNotBlank()) {
            arguments.add("d = \"$dValue\"")
        }

        str += "(" + arguments.joinToString { it } + ")"

        return str
    }

    companion object {
        private val ATTR_D = "d"
        fun createPathNode(node: Node): PathNode {
            val htmlAttrs = node.attributes()
            val dValue = htmlAttrs.get(ATTR_D).capitalize()
            htmlAttrs.remove(ATTR_D)
            val attrs = htmlAttrs.map { ComposeAttribute(it.key, it.value) }
            return PathNode(attrs, dValue)
        }
    }

}