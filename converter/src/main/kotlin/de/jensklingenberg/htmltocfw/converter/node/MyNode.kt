package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.node.ANode.Companion.createAnode
import de.jensklingenberg.htmltocfw.converter.node.FormNode.Companion.createForm
import de.jensklingenberg.htmltocfw.converter.node.ImgNode.Companion.createImg
import de.jensklingenberg.htmltocfw.converter.node.InputNode.Companion.createInput
import de.jensklingenberg.htmltocfw.converter.node.LabelNode.Companion.createLabel
import de.jensklingenberg.htmltocfw.converter.node.OptGroupNode.Companion.createOptGroupNode
import de.jensklingenberg.htmltocfw.converter.node.PathNode.Companion.createPathNode
import de.jensklingenberg.htmltocfw.converter.node.TextAreaNode.Companion.createTextArea
import org.jsoup.nodes.Comment
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

class LayoutNode(private val children: List<MyNode>) {

    fun accept(visitor: Visitor) {
        children.forEach { it.accept(visitor) }
    }
}



interface MyNode {
    fun accept(visitor: Visitor)
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
                    createAnode(node)
                }
                "form" -> {
                    createForm(node)
                }
                "img" -> {
                    createImg(node)
                }
                "input" -> {
                    createInput(node)
                }
                "label" -> {
                    createLabel(node)

                }
                "meta" -> {
                    EmptyNode()
                }
                "option" -> {
                    OptionNode(node.attributes(), node.childNodes())
                }
                "optgroup" -> {
                    createOptGroupNode(node)
                }
                "path" -> {
                    createPathNode(node)
                }
                "textarea" -> {
                    createTextArea(node)
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
                    GenericNode(node.tagName(), node.childNodes(), node.attributes())
                }
            }
        }
        else -> {
            println("Not found: ${node.nodeName()}")
            return EmptyNode()
        }

    }


}

