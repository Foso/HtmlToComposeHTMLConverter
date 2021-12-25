package de.jensklingenberg.htmltocfw.converter.node

import org.jsoup.nodes.Comment
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

interface MyNode {
    fun print(): String
}


fun getMyNode(node: Node): MyNode {
    return when (node) {
        is TextNode -> {
            CFWTextNode(node)
        }
        is Comment -> {
            CFWComment(node)
        }
        is Element -> {
            when (node.tagName()) {
                "a" -> {
                    ANode(node)
                }
                "form" -> {
                    FormNode(node)
                }
                "img" -> {
                    ImgNode(node.attributes())
                }
                "input" -> {
                    InputNode(node.attributes())
                }
                "label" -> {
                    LabelNode(node)
                }
                "meta" ->{
                    EmptyNode()
                }
                "option" -> {
                    OptionNode(node)
                }
                "optgroup" -> {
                    OptGroupNode(node)
                }
                "path" -> {
                    PathNode(node)
                }
                "textarea" -> {
                    TextAreaNode(node)
                }
                "script" -> {
                    EmptyNode()
                }
                "title" -> {
                    TitleNode(node)
                }
                "style" -> {
                    StyleNode(node)
                }

                else -> {
                    GenericNode(node)
                }
            }
        }
        else -> {
            println("Not found: ${node.nodeName()}")
            return EmptyNode()
        }

    }


}
