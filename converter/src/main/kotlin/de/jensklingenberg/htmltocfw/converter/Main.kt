package de.jensklingenberg.htmltocfw.converter

import org.jsoup.Jsoup
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Comment
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import java.io.File

fun main() {

    val html = File("/home/jens/Code/2021/jk/HtmlToComposeWeb/converter/src/de.jensklingenberg.htmltocfw.converter.main/kotlin/html.text").readText()

    val text = htmlToCompose(html)

    File("/home/jens/Code/2021/jk/HtmlToComposeWeb/converter/src/de.jensklingenberg.htmltocfw.converter.main/kotlin/Result.txt").writeText(text)
}

fun htmlToCompose(html:String): String {
    val doc = Jsoup.parse(html);

    val text = doc.body().childNodes().joinToString(separator = "") {
        getMyNode(it).print()
    }

    return text
}

interface MyNode {
    fun print(): String
}

fun printStyle(it: Attribute): String {
    var str = "style {"

    //TODO Number oder StrinG?
    val styles = it.value.split(";")
    styles.filter { it.isNotEmpty() }.forEach {
        val (propName, propValue) = it.split(":")
        val escapedProp = propValue.replace("\"", "\\\"")
        str += "property(\"$propName\",\"${escapedProp}\")"
    }
    return "$str}"
}


fun printAttributes(attributesList: List<Attribute>): String {
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
            "style" -> {
                printStyle(attribute)
            }
            "required", "hidden", "selected", "disabled",  -> {
                attribute.key + "()"
            }
            "readonly"-> {
               "readOnly()"
            }

            "id" -> {
                "id(\"${attribute.value}\")"
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


fun getMyNode(ele: Node): MyNode {
    return when (ele) {
        is TextNode -> {
            MyTextNode(ele)
        }
        is Comment -> {
            MyComment(ele)
        }
        is Element -> {
            when (ele.tagName()) {
                "a"->{
                    ANode(ele)
                }
                "form"->{
                    FormNode(ele)
                }
                "img" -> {
                    ImgNode(ele)
                }
                "input" -> {
                    InputNode(ele)
                }
                "label" -> {
                    LabelNode(ele)
                }
                "option" -> {
                    OptionNode(ele)
                }
                "optgroup"->{
                    OptGroupNode(ele)
                }
                "path" -> {
                    PathNode(ele)
                }
                "textarea" -> {
                    TextAreaNode(ele)
                }
                else -> {
                    GenericNode(ele)
                }
            }
        }
        else -> {
            throw Exception("$ele Not found")
        }

    }


}

