package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.getAttributesText
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element

class OptGroupNode(private val element: Element) : MyNode {

    val ATTR_LABEL = "label"

    override fun print(): String {

        var str = "OptGroup ("
        val hasSrc = element.attributes().get(ATTR_LABEL)
        element.attributes().remove(ATTR_LABEL)

        val attributesList: MutableList<Attribute> = element.attributes().asList()

        val attrText = getAttributesText(attributesList)
        str += (attrText)

        if (attrText.isNotBlank()) {
            str += (",")
        }
        str += ("label = \"${hasSrc}\"")
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