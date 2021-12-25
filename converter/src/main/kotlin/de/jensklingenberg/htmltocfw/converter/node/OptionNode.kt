package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.getAttributesText
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element

class OptionNode(private val element: Element) : MyNode {
    val ATTR_VALUE = "value"
    val TAG = "Option"
    override fun print(): String {

        var str = "$TAG ("
        val valueValue = element.attributes().get(ATTR_VALUE)
        element.attributes().remove(ATTR_VALUE)

        val attributesList: MutableList<Attribute> = element.attributes().asList()

        val attrText = getAttributesText(attributesList)
        str += (attrText)

        if (attrText.isNotBlank()) {
            str += (",")
        }
        str += ("value = \"${valueValue}\"")
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