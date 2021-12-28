package de.jensklingenberg.htmltocfw.converter

import org.jsoup.nodes.Attribute

/**
 *
 */
fun getStyleProperties(propName: String, propValue: String): String {

    return when (propName) {

        "background-image" -> {
            "backgroundImage(${propValue})"
        }
        "border-radius" -> {
            val unitType = unitsMap.entries.find { propValue.endsWith(it.value) }

            if (unitType == null) {
                "property(\"$propName\",\"${propValue}\")"
            } else {

                val newValue = propValue.split(" ").joinToString { it.replace(unitType.value, "") + ".${unitType.key}" }
                "borderRadius(${newValue})"
            }
        }
        "box-sizing" -> {
            "boxSizing(\"${propValue}\")"
        }
        "display" -> {
            "display(DisplayStyle.${propValue.capitalize()})"
        }
        "flex-direction" -> {
            "flexDirection(FlexDirection.${propValue.capitalize()})"
        }
        "flex-wrap" -> {
            "flexWrap(FlexWrap.${propValue.capitalize()})"
        }
        "font-family" -> {
            "fontFamily(\"${propValue}\")"
        }
        "font-size" -> {
            val unitType = unitsMap.entries.find { propValue.endsWith(it.value) }

            if (unitType == null) {
                "property(\"$propName\",\"${propValue}\")"
            } else {
                val newValue = propValue.split(" ").joinToString { it.replace(unitType.value, "") + ".${unitType.key}" }
                "fontSize(${newValue})"
            }
        }
        "text-align" -> {
            "textAlign(\"${propValue}\")"
        }

        "text-decoration" -> {
            "textDecoration(\"${propValue}\")"
        }
        "height", "width", "font", "margin", "padding" -> {
            val unitType = unitsMap.entries.find { propValue.endsWith(it.value) }

            if (unitType == null) {
                "property(\"$propName\",\"${propValue}\")"
            } else {

                val newValue =
                    propValue.split(" ").joinToString { it.replace(unitType.value, "") + ".${unitType.key}" }
                "$propName(${newValue})"
            }
        }
        else -> {
            "property(\"$propName\",\"${propValue}\")"
        }
    }

}


fun parseStyleText(attribute: Attribute): String {
    var str = "style {\n"

    //Find better way to parse style
    val styles = attribute.value.split(";")

    styles.filter { it.isNotEmpty() }.map { it.trimStart() }.forEach {
        val propName = it.substringBefore(":").trimStart()
        val propValue = it.substringAfter(":").trimStart().replace("\"", "\\\"")

        str += getStyleProperties(propName, propValue)

        str += "\n"
    }
    return "$str}"
}