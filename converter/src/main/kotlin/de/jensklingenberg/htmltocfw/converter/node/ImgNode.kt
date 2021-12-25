package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.getAttributesText
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element

class ImgNode(val element: Element) : MyNode {
    val ATTR_ALT = "alt"
    val ATTR_SRC = "src"

    override fun print(): String {

        var str = "Img ("
        val altValue = element.attributes().get(ATTR_ALT)
        element.attributes().remove(ATTR_ALT)
        val srcValue = element.attributes().get(ATTR_SRC)
        element.attributes().remove(ATTR_SRC)

        val attributesList: MutableList<Attribute> = element.attributes().asList()

        val attrText = getAttributesText(attributesList)
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