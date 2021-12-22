package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.getAttributesText
import org.jsoup.nodes.Element

class PathNode(private val element: Element) : MyNode {

    override fun print(): String {
        var str = "Path ("
        val dValue = element.attributes().get("d")
        element.attributes().remove("d")

        str += (getAttributesText(element.attributes().asList()))

        if (dValue.isNotBlank()) {
            val type = dValue.capitalize()
            str += (",d = \"$type\"")
        }

        str += (")")

        return str
    }

}