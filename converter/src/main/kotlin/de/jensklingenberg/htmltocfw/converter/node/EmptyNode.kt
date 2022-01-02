package de.jensklingenberg.htmltocfw.converter.node

class EmptyNode : MyNode {
    override fun accept(visitor: Visitor) {
        visitor.visitEmpty(this)
    }

    override fun toString(): String {
        return ""
    }
}