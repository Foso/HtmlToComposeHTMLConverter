package de.jensklingenberg.htmltocfw.converter

import de.jensklingenberg.htmltocfw.converter.node.ComposeAttribute
import org.jsoup.nodes.Attribute

fun parseAttributes(attributesList: List<Attribute>): String {
    return parseAttributes(attributesList.map { ComposeAttribute(it.key,it.value) })
}

/**
 *
 */
@JvmName("parseAttributes1")
fun parseAttributes(attributesList: List<ComposeAttribute>): String {

    var str = ""
    attributesList.forEachIndexed { index, attribute ->
        if (index == 0) {
            str += "attrs = {\n"
        }
        str += when (attribute.key) {
            "class" -> {
                val classes = attribute.value.split(" ").joinToString { "\"$it\"" }
                "classes($classes)"
            }
            "draggable" -> {
                "draggable(Draggable.${attribute.value.capitalize()})"
            }
            "style" -> {
                parseStyleText(ComposeAttribute(attribute.key,attribute.value))
            }
            "required", "hidden", "selected", "disabled" -> {
                attribute.key + "()"
            }
            "readonly" -> {
                "readOnly()"
            }

            "id", "name", "rowspan", "colspan" -> {
                val attrValue = if (attribute.value.isNumber) {
                    attribute.value
                } else {
                    formatStringValue(attribute.value)
                }

                "${attribute.key}($attrValue)"
            }

            else -> {
                "attr(\"${attribute.key}\",\"${attribute.value}\")"
            }
        }
        str += "\n"

        if (index == attributesList.lastIndex) {
            str += "}"
        }
    }
    return str
}