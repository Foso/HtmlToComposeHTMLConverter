package de.jensklingenberg.htmltocfw.converter.node

class TitleNode(private val titleText: String) : MyNode {

    override fun toString(): String {
        return "document.title=\"${titleText}\"\n"
    }
}