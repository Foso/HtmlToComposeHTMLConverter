package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Element

class PathNode(private val element: Element) : MyNode {
    val ATTR_D = "d"
    val TAG = "Path"
    override fun print(): String {
        var str = "$TAG ("
        val dValue = element.attributes().get(ATTR_D)
        element.attributes().remove(ATTR_D)

        str += (parseAttributes(element.attributes().asList()))

        if (dValue.isNotBlank()) {
            val type = dValue.capitalize()
            str += (",d = \"$type\"")
        }

        str += (")")

        return str
    }

}