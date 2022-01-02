package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Label]
 */

class LabelNode(
    private val attrs: List<ComposeAttribute>,
    private val childNodes: List<Node>,
    val forIdValue: String = ""
) :
    MyNode {
    private val ATTR_FOR = "for"
    private val TAG = "Label"
    override fun accept(visitor: Visitor) {
        visitor.visitLabel(this)
    }

    override fun toString(): String {
        var str = "$TAG ("

        val arguments = mutableListOf<String>()
        if(attrs.isNotEmpty()){
            arguments.add(parseAttributes(attrs))
        }
        arguments.add("forId = \"${forIdValue}\"")

        str += arguments.joinToString { it } + ")"

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

    companion object {
        private val ATTR_FOR = "for"

        fun createLabel(node: Element): LabelNode {
            val htmlAttributes = node.attributes()
            val forValue = htmlAttributes.get(ATTR_FOR)
            htmlAttributes.remove(ATTR_FOR)

            val attrs = node.attributes().map { ComposeAttribute(it.key, it.value) }
            return LabelNode(attrs, node.childNodes(), forValue)
        }
    }

}