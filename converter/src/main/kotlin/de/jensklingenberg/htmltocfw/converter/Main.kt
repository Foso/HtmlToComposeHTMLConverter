package de.jensklingenberg.htmltocfw.converter

import org.jsoup.Jsoup
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Comment
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import java.io.File

fun main() {

    val html = File("/home/jens/Code/2021/jk/HtmlToComposeWeb/converter/src/main/kotlin/de/jensklingenberg/htmltocfw/converter/html.text").readText()

    val text = htmlToCompose(html)

    File("/home/jens/Code/2021/jk/HtmlToComposeWeb/converter/src/main/kotlin/de/jensklingenberg/htmltocfw/converter/Result.txt").writeText(text)
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

fun getStyleText(attribute: Attribute): String {
    var str = "style {\n"

    //TODO Number oder StrinG?
    //Find better way to parse style
    val styles = attribute.value.split(";")

    styles.filter { it.isNotEmpty() }.map { it.trimStart() }.forEach {
        val propName = it.substringBefore(":").trimStart()
        val propValue = it.substringAfter(":").trimStart().replace("\"", "\\\"")

        str += when(propName){
            "font"->{
                "font(\"${propValue}\")"
            }
            "font-family"->{
                "fontFamily(\"${propValue}\")"
            }
            "_font-size"->{
                var unit = propValue
                "fontFamily(\"${propValue}\")"
            }
            "background-image"->{
                "backgroundImage(\"${propValue}\")"
            }
            else ->{
                "property(\"$propName\",\"${propValue}\")"
            }
        }

        str += "\n"
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
            "draggable"->{
                "draggable(Draggable.${attribute.value.capitalize()})"
            }
            "style" -> {
                getStyleText(attribute)
            }
            "required", "hidden", "selected", "disabled",  -> {
                attribute.key + "()"
            }
            "readonly"-> {
               "readOnly()"
            }
            "onclick343"->{
                "onClick { js(\"${attribute.value}\") }"
            }
            "onblur"->{
                "onBlur { js(\"${attribute.value}\") }"
            }
            "ondblclick"->{
                "onDoubleClick { js(\"${attribute.value}\") }"
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

class UnsupportedNode : MyNode{
    override fun print(): String {
        return ""
    }

}

fun getMyNode(node: Node): MyNode {
    return when (node) {
        is TextNode -> {
            MyTextNode(node)
        }
        is Comment -> {
            MyComment(node)
        }
        is Element -> {
            when (node.tagName()) {
                "a"->{
                    ANode(node)
                }
                "form"->{
                    FormNode(node)
                }
                "img" -> {
                    ImgNode(node)
                }
                "input" -> {
                    InputNode(node)
                }
                "label" -> {
                    LabelNode(node)
                }
                "option" -> {
                    OptionNode(node)
                }
                "optgroup"->{
                    OptGroupNode(node)
                }
                "path" -> {
                    PathNode(node)
                }
                "textarea" -> {
                    TextAreaNode(node)
                }
                "script"->{
                    return UnsupportedNode()
                }

                else -> {
                    GenericNode(node)
                }
            }
        }
        else -> {
            println("Not found: ${node.nodeName()}")
            return UnsupportedNode()
        }

    }


}

