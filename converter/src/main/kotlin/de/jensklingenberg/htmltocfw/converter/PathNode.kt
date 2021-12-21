package de.jensklingenberg.htmltocfw.converter

import org.jsoup.nodes.Element

class PathNode(private val element: Element) : MyNode {

    override fun print(): String {
        var str = "Path ("
        val dValue = element.attributes().get("d")
        element.attributes().remove("d")

        str += (printAttributes(element.attributes().asList()))

        if (dValue.isNotBlank()) {
            val type = dValue.capitalize()
            str += (",d = \"$type\"")
        }

        str += (")")

        return str
    }

}