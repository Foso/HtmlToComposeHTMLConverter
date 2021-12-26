package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.TextArea]
 */
class TextAreaNode(private val element: Element) : MyNode {
    val TAG = "TextArea"
    override fun print(): String {
        var str = "$TAG ("
        str += parseAttributes(element.attributes().asList())

        if (element.childNodes().isNotEmpty() && element.childNodes().first() is TextNode) {
            str += ",value= \"${(element.childNodes().first() as TextNode).text()}\""
        }
        str += ")"
        return str
    }

}