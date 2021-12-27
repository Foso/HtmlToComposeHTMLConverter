package de.jensklingenberg.htmltocfw.converter.node

import org.jsoup.nodes.Comment

class CFWComment(private val comment: Comment) : MyNode {
    override fun toString(): String {
        return ("//" + comment.data + "\n")
    }

}