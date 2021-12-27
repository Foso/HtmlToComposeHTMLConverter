package de.jensklingenberg.htmltocfw.converter.node

import org.jsoup.nodes.Comment
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

interface MyNode {

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
                    ANode(node.attributes(), node.childNodes())
                }
                "form" -> {
                    FormNode(node.attributes(), node.childNodes())
                }
                "img" -> {
                    ImgNode(node.attributes())
                }
                "input" -> {
                    InputNode(node.attributes())
                }
                "label" -> {
                    LabelNode(node.attributes(), node.childNodes())
                }
                "meta" ->{
                    EmptyNode()
                }
                "option" -> {
                    OptionNode(node.attributes(), node.childNodes())
                }
                "optgroup" -> {
                    OptGroupNode(node.attributes(), node.childNodes())
                }
                "path" -> {
                    PathNode(node.attributes())
                }
                "textarea" -> {
                    TextAreaNode(node.attributes(), node.childNodes())
                }
                "script" -> {
                    EmptyNode()
                }
                "title" -> {
                    TitleNode(node.text())
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
