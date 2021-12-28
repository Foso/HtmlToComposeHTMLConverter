package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.TextArea]
 */
class TextAreaNode(private val htmlAttributes: Attributes, private val childNodes: List<Node>) : MyNode {
    private val TAG = "TextArea"

    override fun toString(): String {
        var str = "$TAG ("
        str += parseAttributes(htmlAttributes.asList())

        if (childNodes.isNotEmpty() && childNodes.first() is TextNode) {
            str += ",value= \"${(childNodes.first() as TextNode).text()}\""
        }
        str += ")"
        return str
    }

}