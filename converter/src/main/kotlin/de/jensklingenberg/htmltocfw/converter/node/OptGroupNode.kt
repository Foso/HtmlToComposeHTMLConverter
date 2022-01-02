package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Node

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.OptGroup]
 */
class OptGroupNode(
    private val htmlAttributes: List<ComposeAttribute>,
    private val childNodes: List<Node>,
    val label: String
) :
    MyNode {


    private val TAG = "OptGroup"
    override fun accept(visitor: Visitor) {
        visitor.visitOptGroup(this)
    }

    override fun toString(): String {

        var str = "$TAG ("


        val attrText = parseAttributes(htmlAttributes)
        str += attrText

        if (attrText.isNotBlank()) {
            str += (",")
        }
        str += "label = \"${label}\"" + ")"

        val childNodesText = childNodes.joinToString("") {
            getMyNode(it).toString()
        }

        str += "{"
        if (childNodesText.isNotBlank()) {
            str += "\n" + childNodesText + "\n"
        }
        str += "}\n"
        return str
    }

    companion object {
        private val ATTR_LABEL = "label"

        fun createOptGroupNode(node: Node): OptGroupNode {
            val htmlAttributes = node.attributes()
            val hasSrc = htmlAttributes.get(ATTR_LABEL)
            htmlAttributes.remove(ATTR_LABEL)
            val attrs = htmlAttributes.map { ComposeAttribute(it.key, it.value) }
            return OptGroupNode(attrs, node.childNodes(), hasSrc)

        }
    }

}