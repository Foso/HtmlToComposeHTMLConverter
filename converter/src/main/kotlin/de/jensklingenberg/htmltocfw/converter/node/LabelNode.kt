package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import org.jsoup.nodes.Element

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Label]
 */
class LabelNode(private val element: Element) : MyNode {
    val ATTR_FOR = "for"
    val TAG = "Label"

    override fun print(): String {
        var str = "$TAG ("
        val forValue = element.attributes().get(ATTR_FOR)
        element.attributes().remove(ATTR_FOR)

        val attrText = parseAttributes(element.attributes().asList())
        str += attrText

        if (forValue.isNotBlank()) {
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