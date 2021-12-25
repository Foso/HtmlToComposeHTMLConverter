package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.getAttributesText
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element

class ANode(private val element: Element) : MyNode {

    val ATTR_HREF = "href"

    override fun print(): String {

        var str = "A ("
        val hrefValue = element.attributes().get(ATTR_HREF)
        element.attributes().remove(ATTR_HREF)

        val attributesList: MutableList<Attribute> = element.attributes().asList()

        val attrText = getAttributesText(attributesList)
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