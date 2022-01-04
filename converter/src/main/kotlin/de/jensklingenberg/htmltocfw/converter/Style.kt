package de.jensklingenberg.htmltocfw.converter

import de.jensklingenberg.htmltocfw.converter.node.ComposeAttribute
import de.jensklingenberg.htmltocfw.converter.node.withEscapedSymbol


fun parsePropertyValueWithCssUnit(propName: String, propValue: String, composePropName: String): String {
    val unitType = unitsMap.entries.find { propValue.endsWith(it.value) }

    return if (unitType == null) {
        "$composePropName(\"${propValue}\")"
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
        "border" -> {
            val animationPropertyList = propValue.split(" ")
            val widthValue = animationPropertyList.getOrNull(0)
            val lineStyle = animationPropertyList.getOrNull(1)
            val color = animationPropertyList.getOrNull(2)
            var str = "border {\n"
            widthValue?.let {
                str += parseStyleProperties("width", widthValue) + "\n"
            }
            color?.let {
                str += parseStyleProperties("color", it) + "\n"
            }
            lineStyle?.let {
                str += "style(LineStyle.${it.capitalize()})" + "\n"
            }

            str += "\n}"
            str
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

        "background-image", "border-radius", "box-sizing",  "font-size", "letter-spacing", "text-align", "text-decoration" -> {
            //font-size --> fontSize
            val (first, second) = propName.split("-")
            val composePropName = first + second.capitalize()
            parsePropertyValueWithCssUnit(propName, propValue.withEscapedSymbol(), composePropName)
        }

        "cursor", "duration", "font", "height", "width", "margin", "padding", "left", "top", "bottom" -> {
            parsePropertyValueWithCssUnit(propName, propValue, propName)
        }
        else -> {
            val fixValue = propValue.replace("\"", "\\\"")
            "property(\"$propName\",\"${fixValue}\")"
        }
    }

}


fun parseStyleText(attribute: ComposeAttribute): String {
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