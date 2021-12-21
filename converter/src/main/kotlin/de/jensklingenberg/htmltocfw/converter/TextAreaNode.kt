package de.jensklingenberg.htmltocfw.converter

import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

class TextAreaNode(private val element: Element) : MyNode {

    override fun print(): String {
        var str = "TextArea("
        str += printAttributes(element.attributes().asList())

        if(element.childNodes().isNotEmpty() && element.childNodes().first() is TextNode){
            str += ",value= \"${(element.childNodes().first() as TextNode).text()}\""
        }
        str += ")"
        return str
    }

}