package de.jensklingenberg.htmltocfw.converter

import org.jsoup.nodes.Attribute


fun parsePropertyValueWithCssUnit(propName: String, propValue: String, composePropName: String): String {
    val unitType = unitsMap.entries.find { propValue.endsWith(it.value) }

    return if (unitType == null) {
        "property(\"$propName\",\"${propValue}\")"
    } else {
        val newValue = propValue.split(" ").joinToString { it.replace(unitType.value, ".${unitType.key}") }
        "$composePropName(${newValue})"
    }

}

/**
 *
 */
fun parseStyleProperties(propName: String, propValue: String): String {

    return when (propName) {
        "animation" -> {
            var str = ""
            val animationPropertyList = propValue.split(" ")
            val keyFrameName = animationPropertyList.getOrNull(0)
            val duration = animationPropertyList.getOrNull(1)
            val iterationCount = animationPropertyList.getOrNull(2)

            keyFrameName?.let {
                str += "animation(${keyFrameName}){\n"
            }
            duration?.let {
                str += parseStyleProperties("duration", duration) + "\n"
            }

            iterationCount?.let {
                str += "iterationCount = listOf(" + if (iterationCount == "infinite") {
                    "null)"
                } else {
                    "$it)"
                }

            }

            str += "}"
            str
        }
        "animation-duration", "animation-delay" -> {
            //Ignore
            ""
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
        "position" -> {
            "position(Position.${propValue.capitalize()})"
        }

        "background-image", "border-radius", "box-sizing", "font-family", "font-size", "letter-spacing", "text-align", "text-decoration" -> {
            //font-size --> fontSize
            val (first, second) = propName.split("-")
            val composePropName = first + second.capitalize()
            parsePropertyValueWithCssUnit(propName, propValue, composePropName)
        }

        "cursor", "duration", "font", "height", "width", "margin", "padding", "left", "top", "bottom" -> {
            parsePropertyValueWithCssUnit(propName, propValue, propName)
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

        str += parseStyleProperties(propName, propValue)

        str += "\n"
    }
    return "$str}"
}