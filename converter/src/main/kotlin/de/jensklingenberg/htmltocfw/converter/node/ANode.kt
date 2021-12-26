package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.A]
 */
class ANode(private val element: Element) : MyNode {
    val TAG = "A"
    val ATTR_HREF = "href"

    override fun print(): String {

        var str = "$TAG ("
        val hrefValue = element.attributes().get(ATTR_HREF)
        element.attributes().remove(ATTR_HREF)

        val attributesList: MutableList<Attribute> = element.attributes().asList()

        val attrText = parseAttributes(attributesList)
        str += attrText

        if (hrefValue.isNotBlank()) {
            if (attrText.isNotBlank()) {
                str += (", ")
            }
        }
        str += ("href = \"${hrefValue}\"")
        str += (")")

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