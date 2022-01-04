package de.jensklingenberg.htmltocfw.converter.node

import de.jensklingenberg.htmltocfw.converter.visitor.Visitor

class EmptyNode : MyNode {
    override fun accept(visitor: Visitor) {
        visitor.visitEmpty(this)
    }

    override fun toString(): String {
        return ""
    }
}