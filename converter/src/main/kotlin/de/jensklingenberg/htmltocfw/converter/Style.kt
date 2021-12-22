package de.jensklingenberg.htmltocfw.converter

import org.jsoup.nodes.Attribute


fun getStyleText(attribute: Attribute): String {
    var str = "style {\n"

    //Find better way to parse style
    val styles = attribute.value.split(";")

    styles.filter { it.isNotEmpty() }.map { it.trimStart() }.forEach {
        val propName = it.substringBefore(":").trimStart()
        val propValue = it.substringAfter(":").trimStart().replace("\"", "\\\"")

        str += when (val test = CFWAttributeNames.values().find { it.htmlAttrName == propName }) {

            CFWAttributeNames.MARGIN, CFWAttributeNames.PADDING, CFWAttributeNames.FONTSIZE, CFWAttributeNames.BORDERRADIUS -> {
                val unitType = unitsMap.entries.find { propValue.endsWith(it.value) }

                if (unitType == null) {
                    "property(\"$propName\",\"${propValue}\")"
                } else {
                    val newValue = propValue.replace(unitType.value, "") + ".${unitType.key}"
                    "${test.cfwAttrName}(${newValue})"
                }
            }

            CFWAttributeNames.TEXTDECORATION, CFWAttributeNames.TEXTALIGN, CFWAttributeNames.BACKGROUNDIMAGE, CFWAttributeNames.FONTFAMILY, CFWAttributeNames.FONT -> {
                "${test.cfwAttrName}(\"${propValue}\")"
            }
            else -> {
                "property(\"$propName\",\"${propValue}\")"
            }
        }

        str += "\n"
    }
    return "$str}"
}