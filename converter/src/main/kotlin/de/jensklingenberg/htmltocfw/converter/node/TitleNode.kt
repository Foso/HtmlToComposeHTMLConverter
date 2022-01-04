package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.visitor.Visitor

class TitleNode(private val titleText: String) : MyNode {
    override fun accept(visitor: Visitor) {
        visitor.visitTitle(this)
    }

    override fun toString(): String {
        return "document.title=\"${titleText}\"\n"
    }
}