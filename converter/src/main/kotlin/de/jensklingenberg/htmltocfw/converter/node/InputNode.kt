package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.getAttributesText
import org.jsoup.nodes.Element

class InputNode(private val element: Element) : MyNode {
    val ATTR_TYPE = "type"
    override fun print(): String {
        var str = "Input ("
        val hasType = element.attributes().get(ATTR_TYPE)
        element.attributes().remove(ATTR_TYPE)
        val attrText = getAttributesText(element.attributes().asList())
        str += attrText

        if (hasType.isNotBlank()) {
            if (attrText.isNotBlank()) {
                str += (", ")
            }
            val type = hasType.capitalize()
            str += ("type = InputType.${type}")
        }

        str += ")\n"
        return (str)
    }

}