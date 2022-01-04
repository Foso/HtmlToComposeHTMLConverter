package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import de.jensklingenberg.htmltocfw.converter.visitor.Visitor
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.TextArea]
 */
class TextAreaNode(
    private val attrs: Attributes,
    val value: String? = null
) : MyNode {
    private val TAG = "TextArea"
    override fun accept(visitor: Visitor) {
        visitor.visitTextArea(this)
    }

    override fun toString(): String {
        var str = "$TAG "

        val arguments = mutableListOf(parseAttributes(attrs.asList()))

        if (value != null) {
            arguments.add("value = $value")
        }

        str += "("+arguments.joinToString { it } +")"
        str += "\n"
        return str
    }

    companion object {
        fun createTextArea(node: Element): TextAreaNode {
            val childNodes = node.childNodes()
            val valueValue = if (childNodes.isNotEmpty() && childNodes.first() is TextNode) {
                node.attributes().remove("value")
                "\"${(childNodes.first() as TextNode).text()}\""
            } else {
                null
            }

            return TextAreaNode(node.attributes(), valueValue)
        }
    }

}