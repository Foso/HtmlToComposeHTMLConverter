package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attributes

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Img]
 * @param htmlAttributes A list of HTML Attributes for < img>
 */
class ImgNode(private val htmlAttributes: Attributes) : MyNode {
    val ATTR_ALT = "alt"
    val ATTR_SRC = "src"
    val TAG = "Img"

    override fun toString(): String {

        var str = "$TAG ("
        val altValue = htmlAttributes.get(ATTR_ALT)
        htmlAttributes.remove(ATTR_ALT)
        val srcValue = htmlAttributes.get(ATTR_SRC)
        htmlAttributes.remove(ATTR_SRC)

        val attrText = parseAttributes(htmlAttributes.asList())
        str += attrText
        if (attrText.isNotBlank()) {
            str += (", ")
        }
        str += ("src = \"${srcValue}\"")
        str += (", alt = \"${altValue}\"")
        str += (")\n")

        return str
    }

}