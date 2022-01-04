package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import de.jensklingenberg.htmltocfw.converter.visitor.Visitor
import org.jsoup.nodes.Node

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Option]
 */
class OptionNode(private val attributes: List<ComposeAttribute>, val childNodes: List<Node>, val valueValue: String) :
    MyNode {
    private val TAG = "Option"
    override fun accept(visitor: Visitor) {
        visitor.visitOption(this)
    }

    override fun toString(): String {

        var str = "$TAG ("

        val arguments = mutableListOf<String>()
        if (attributes.isNotEmpty()) {
            arguments.add(parseAttributes(attributes))
        }

        arguments.add(("value = \"${valueValue}\""))

        str += arguments.joinToString { it } + ")"

        str += "{"

        val childNodesText = childNodes.joinToString("") {
            getMyNode(it).toString()
        }

        if (childNodesText.isNotBlank()) {
            str += "\n" + childNodesText + "\n"
        }
        str += ("}\n")
        return str
    }

    companion object {
        private val ATTR_VALUE = "value"

        fun createOptionNode(node: Node): OptionNode {
            val htmlAttributes = node.attributes()
            val valueValue = htmlAttributes.get(ATTR_VALUE)
            htmlAttributes.remove(ATTR_VALUE)
            val attrs = htmlAttributes.map { ComposeAttribute(it.key, it.value) }
            return OptionNode(attrs, node.childNodes(), valueValue)
        }
    }
}