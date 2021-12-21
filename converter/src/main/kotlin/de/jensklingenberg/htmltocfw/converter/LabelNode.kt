package de.jensklingenberg.htmltocfw.converter

import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element

class LabelNode(private val element: Element) : MyNode {

    override fun print(): String {

        var str = "Label ("
        val forValue = element.attributes().get("for")
        element.attributes().remove("for")

        val attributesList: MutableList<Attribute> = element.attributes().asList()

        val attrText = printAttributes(attributesList)
        str += attrText

        if(forValue.isNotBlank()){
            if (attrText.isNotBlank()) {
                str += (", ")
            }
        }
        str += ("forId = \"${forValue}\"")
        str += (")")

        val childNodesText = element.childNodes().joinToString(separator = "") {
            getMyNode(it).print()
        }

        str += "{"
        if(childNodesText.isNotBlank()){
            str += "\n"
        }
        str += childNodesText
        if(childNodesText.isNotBlank()){
            str += "\n"
        }
        str += ("}\n")
        return str
    }

}