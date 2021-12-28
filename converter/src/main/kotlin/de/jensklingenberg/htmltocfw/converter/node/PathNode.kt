package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes

class PathNode(private val htmlAttributes: Attributes) : MyNode {
    private val ATTR_D = "d"
    private val TAG = "Path"

    override fun toString(): String {
        var str = "$TAG ("
        val dValue = htmlAttributes.get(ATTR_D)
        htmlAttributes.remove(ATTR_D)

        str += parseAttributes(htmlAttributes.asList())

        if (dValue.isNotBlank()) {
            val type = dValue.capitalize()
            str += (",d = \"$type\"")
        }

        str += (")")

        return str
    }

}