package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.parseAttributes
import de.jensklingenberg.htmltocfw.converter.visitor.Visitor
import org.jsoup.nodes.Element

/**
 * This class generates the code for
 * [org.jetbrains.compose.web.dom.Img]
 * @param htmlAttributes A list of HTML Attributes for < img>
 */
class ImgNode(private val attrs: List<ComposeAttribute>, val src: String = "", val alt: String? = null) : MyNode {

    private val TAG = "Img"
    override fun accept(visitor: Visitor) = visitor.visitImg(this)

    override fun toString(): String {

        var str = "$TAG "

        val arguments = mutableListOf<String>()

        if (attrs.isNotEmpty()) {
            arguments.add(parseAttributes(attrs))
        }

        arguments.add("src = \"${src}\"")

        alt?.let {
            arguments.add("alt = \"${it}\"")
        }

        str += "(" + arguments.joinToString { it } + ")"
        str += "\n"

        return str
    }

    companion object {
        private val ATTR_ALT = "alt"
        private val ATTR_SRC = "src"

        fun createImg(node: Element): ImgNode {
            val htmlAttributes = node.attributes()
            val altValue = htmlAttributes.get(ATTR_ALT)
            htmlAttributes.remove(ATTR_ALT)
            val srcValue = htmlAttributes.get(ATTR_SRC)
            htmlAttributes.remove(ATTR_SRC)
            return ImgNode(node.attributes().map { ComposeAttribute(it.key, it.value) }, srcValue, altValue)
        }
    }

}