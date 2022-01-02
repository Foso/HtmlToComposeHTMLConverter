package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Node

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Form]
 */
class FormNode(
    private val attrs: List<ComposeAttribute>,
    private val childNodes: List<Node>,
    val actionValue: String = ""
) :
    MyNode {
    private val TAG = "Form"

    override fun accept(visitor: Visitor) {
        visitor.visitForm(this)
    }

    override fun toString(): String {
        var str = "$TAG ("

        val arguments = mutableListOf<String>()
        if(attrs.isNotEmpty()){
            arguments.add(parseAttributes(attrs))
        }
        arguments.add("action = \"${actionValue}\"")

        str += arguments.joinToString { it } + ")"

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

    companion object {
        private val ATTR_ACTION = "action"

        fun createForm(node: Node): FormNode {
            val htmlAttributes = node.attributes()
            val actionValue = htmlAttributes.get(ATTR_ACTION)
            htmlAttributes.remove(ATTR_ACTION)
            val attrs = htmlAttributes.map { ComposeAttribute(it.key, it.value) }
            return FormNode(attrs, node.childNodes(), actionValue)
        }
    }

}