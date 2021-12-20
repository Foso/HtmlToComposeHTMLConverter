import org.jsoup.Jsoup
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

fun main() {
    //println("Hello World!")
    val html = """
<div class="carousel rounded-box">
  <div class="carousel-item">
    <img src="https://picsum.photos/id/500/256/144">
  </div> 
  <div class="carousel-item">
    <img src="https://picsum.photos/id/501/256/144">
  </div> 
  <div class="carousel-item">
    <img src="https://picsum.photos/id/502/256/144">
  </div> 
  <div class="carousel-item">
    <img src="https://picsum.photos/id/503/256/144">
  </div> 
  <div class="carousel-item">
    <img src="https://picsum.photos/id/504/256/144">
  </div> 
  <div class="carousel-item">
    <img src="https://picsum.photos/id/505/256/144">
  </div> 
  <div class="carousel-item">
    <img src="https://picsum.photos/id/506/256/144">
  </div>
</div>

""";
    val doc = Jsoup.parse(html);
    doc

    printDoc(doc)
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.

}

fun printDoc(doc: Document) {
    doc.body().childNodes().forEach {
        printNode(it)
    }

}

fun printNode(element: Node) {
    getMyNode(element).print()

}

interface MyNode {
    fun print()
}

fun printStyle(it: Attribute) {
    println("style{")
    //TODO Number oder StrinG?
    val styles = it.value.split(";")
    styles.filter { it.isNotEmpty() }.forEach {
        val (propName, propValue) = it.split(": ")
        val escpaedProp = propValue.replace("\"", "\\\"")
        println("property(\"$propName\",\"${escpaedProp}\")")
    }
    println("}")
}

class InputNode(val element: Element) : MyNode {
    override fun print() {
        println(element.tag().toString().capitalize() + "(")
        val hasType = element.attributes().hasKey("type")
        if (hasType) {
            val type = element.attributes().get("type").capitalize()
            println("type=InputType.${type},")
        }
        println("attrs={")
        element.attributes().asList().forEach {
            when (it.key) {
                "type" -> {}
                "class" -> {
                    val classes = it.value.split(" ").joinToString { "\"$it\"" }
                    println("classes($classes)")
                }
                "style" -> {
                    printStyle(it)
                }
                else -> {
                    printAttribute(it)
                }
            }
        }
        println("})")
    }

}


class TextAreaNode(val element: Element) : MyNode {

    override fun print() {
        println("TextArea(")

        val attributesList = element.attributes().asList()
        attributesList.forEachIndexed { index, attribute ->
            if (index == 0) {
                println("attrs={")
            }
            when (attribute.key) {
                "class" -> {
                    val classes = attribute.value.split(" ").joinToString { "\"$it\"" }
                    println("classes($classes)")
                }
                "style" -> {
                    printStyle(attribute)
                }
                else -> {
                    printAttribute(attribute)
                }
            }
            if (index == attributesList.lastIndex) {
                println("}")
            }
        }

    }

}

class ImgNode(val element: Element) : MyNode {

    override fun print() {
        println(element.tag().toString().capitalize() + "(")
        val hasSrc = element.attributes().hasKey("src")
        if (hasSrc) {
            println("src=\"${element.attributes().get("src")}\"")
        }

        element.attributes().remove("src")

        val attributesList = element.attributes().asList()
        attributesList.forEachIndexed { index, attribute ->
            if (index == 0) {
                println(",attrs={")
            }
            when (attribute.key) {

                "class" -> {
                    val classes = attribute.value.split(" ").joinToString { "\"$it\"" }
                    println("classes($classes)")
                }
                "style" -> {
                    printStyle(attribute)
                }
                else -> {
                    printAttribute(attribute)
                }
            }
            if (index == attributesList.lastIndex) {
                println("}")
            }
        }
        println(")")

    }

}

class MyTextNode(private val textNode: TextNode) : MyNode {
    override fun print() {
        if (textNode.text().isNotBlank()) {
            println("Text(\"${textNode.text()}\")")
        }
    }

}

fun printAttribute(attributes: Attribute) {
    println("attr(\"${attributes.key}\",\"${attributes.value}\")")
}

class GenericNode(val element: Element) : MyNode {

    override fun print() {
        println(element.tag().toString().capitalize() + "(")
        val attributesList = element.attributes().asList()
        attributesList.forEachIndexed { index, attribute ->
            if (index == 0) {
                println("attrs={")
            }
            when (attribute.key) {
                "class" -> {
                    val classes = attribute.value.split(" ").joinToString { "\"$it\"" }
                    println("classes($classes)")
                }
                "style" -> {
                    printStyle(attribute)
                }
                else -> {
                    printAttribute(attribute)
                }
            }
            if (index == attributesList.lastIndex) {
                println("}")
            }
        }
        println("){")
        element.childNodes().forEach {
            printNode(it)
        }

        println("}")
    }
}


fun getMyNode(ele: Node): MyNode {
    return when (ele) {
        is TextNode -> {
            MyTextNode(ele)
        }
        is Element -> {
            when (ele.tagName()) {
                "img" -> {
                    ImgNode(ele)
                }
                "input" -> {
                    InputNode(ele)
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

fun printEle(element: Element) {
    println(element.tag().toString().capitalize() + "(")
    println("attrs={")
    element.attributes().asList().forEach {
        when (it.key) {
            "class" -> {
                val classes = it.value.split(" ").joinToString { "\"$it\"" }
                println("classes($classes)")
            }
            "style" -> {
                println("style{")
                //TODO Number oder StrinG?
                val styles = it.value.split(";")
                styles.filter { it.isNotEmpty() }.forEach {
                    val (propName, propValue) = it.split(": ")
                    val escpaedProp = propValue.replace("\"", "\\\"")
                    println("property(\"$propName\",\"${escpaedProp}\")")
                }
                println("}")
            }
            else -> {
                println(it)
            }
        }
    }
    println("}){")
    element.childNodes().forEach {
        printNode(it)
    }

    println("}")


}