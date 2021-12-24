package de.jensklingenberg.htmltocfw.converter.node

import org.jsoup.nodes.Element

class TitleNode(private val element: Element) : MyNode {

    override fun print(): String {
        return "document.title=\"${this.element.text()}\"\n"
    }
}