package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.A]
 */
class ANode(
    val childNodes: List<Node>,
    val href: String = "",
    val attrs: List<ComposeAttribute>
) : MyNode {
    val composableName = "A"

    override fun accept(visitor: Visitor) {
        visitor.visitA(this)
    }

    override fun toString(): String {

        var str = "$composableName ("
        val hrefValue = href

        val arguments = mutableListOf<String>()

        if(attrs.isNotEmpty()){
            arguments.add(parseAttributes(attrs))
        }

        arguments.add("href =\"${hrefValue}\"")

        str += arguments.joinToString { it } + ")"

        val childNodesText = childNodes.joinToString(separator = "") {
            getMyNode(it).toString()
        }

        str += "{"

        if (childNodesText.isNotBlank()) {
            str += "\n" + childNodesText + "\n"
        }

        str += ("}\n")
        return str
    }

    companion object {

        fun createAnode(node: Element): ANode {
            val htmlAttributes = node.attributes()
            val hrefValue = htmlAttributes.get("href")
            htmlAttributes.remove("href")

            val compo = htmlAttributes.asList().map { ComposeAttribute(it.key, it.value) }
            return ANode( node.childNodes(), hrefValue, compo)

        }
    }

}

data class ComposeParameter(val name: String, val value: String)

