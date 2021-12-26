package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Img]
 */
class ImgNode(val element: Attributes) : MyNode {
    val ATTR_ALT = "alt"
    val ATTR_SRC = "src"
    val TAG = "Img"

    override fun print(): String {

        var str = "$TAG ("
        val altValue = element.get(ATTR_ALT)
        element.remove(ATTR_ALT)
        val srcValue = element.get(ATTR_SRC)
        element.remove(ATTR_SRC)

        val attrText = parseAttributes(element.asList())
        str += (attrText)
        if (attrText.isNotBlank()) {
            str += (", ")
        }
        str += ("src = \"${srcValue}\"")
        str += (", alt = \"${altValue}\"")
        str += (")\n")

        return str
    }

}