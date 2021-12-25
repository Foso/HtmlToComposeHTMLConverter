package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.getAttributesText
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element

class FormNode(private val element: Element) : MyNode {
    val TAG = "Form"
    val ATTR_ACTION = "action"

    override fun print(): String {
        var str = "$TAG ("
        val actionValue = element.attributes().get(ATTR_ACTION)
        element.attributes().remove(ATTR_ACTION)

        val attributesList: MutableList<Attribute> = element.attributes().asList()

        val attrText = getAttributesText(attributesList)
        str += attrText

        if (actionValue.isNotBlank()) {
            if (attrText.isNotBlank()) {
                str += (", ")
            }
        }
        str += ("action = \"${actionValue}\"")
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