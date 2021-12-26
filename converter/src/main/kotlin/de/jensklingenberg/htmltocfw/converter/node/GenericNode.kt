package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Element

class GenericNode(private val element: Element) : MyNode {

    override fun print(): String {
        var str = element.tag().toString().capitalize() + " "

        val attrs = parseAttributes(element.attributes().asList())
        if (attrs.isNotBlank()) {
            str += "($attrs)"
        }

        val childNodesText = element.childNodes().joinToString(separator = "") {
            getMyNode(it).print()
        }

        str += "{"
        if (childNodesText.isNotBlank()) {
            str += "\n"
        }
        str += childNodesText
        if (childNodesText.isNotBlank()) {
            str += "\n"
        }
        str += ("}\n")
        return str
    }
}