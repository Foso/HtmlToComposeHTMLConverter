package de.jensklingenberg.htmltocfw.converter

import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element

class OptGroupNode(private val element: Element) : MyNode {

    override fun print(): String {

        var str = "OptGroup ("
        val hasSrc = element.attributes().get("label")
        element.attributes().remove("label")

        val attributesList: MutableList<Attribute> = element.attributes().asList()

        val attrText = printAttributes(attributesList)
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