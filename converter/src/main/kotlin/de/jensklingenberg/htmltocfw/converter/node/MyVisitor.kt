package de.jensklingenberg.htmltocfw.converter.node

interface Visitor {
    fun visitText(textNode: CFWTextNode)
    fun visitGeneric(genericNode: GenericNode)
    fun visitStyle(styleNode: StyleNode)
    fun visitLabel(labelNode: LabelNode)
    fun visitImg(node: ImgNode)
    fun visitInput(node: InputNode)
    fun visitForm(node: FormNode)
    fun visitOptGroup(optGroupNode: OptGroupNode)
    fun visitOption(optionNode: OptionNode)
    fun visitPath(pathNode: PathNode)
    fun visitTextArea(textAreaNode: TextAreaNode)
    fun visitTitle(titleNode: TitleNode)
    fun visitComment(cfwComment: CFWComment)
    fun visitA(aNode: ANode)
    fun visitEmpty(emptyNode: EmptyNode)
}

class MyVisitor : Visitor {

    var text: String = ""

    fun getResult(): String {
        return text
    }

    override fun visitText(textNode: CFWTextNode) {
        text += textNode.toString()
    }

    override fun visitGeneric(genericNode: GenericNode) {
        text += genericNode.toString()
    }

    override fun visitStyle(styleNode: StyleNode) {
        text += styleNode.toString()
    }

    override fun visitLabel(labelNode: LabelNode) {
        text += labelNode.toString()
    }

    override fun visitImg(node: ImgNode) {
        text += node.toString()
    }

    override fun visitInput(node: InputNode) {
        text += node.toString()
    }

    override fun visitForm(node: FormNode) {
        text += node.toString()
    }

    override fun visitOptGroup(optGroupNode: OptGroupNode) {
        text += optGroupNode.toString()
    }

    override fun visitOption(optionNode: OptionNode) {
        text += optionNode.toString()
    }

    override fun visitPath(pathNode: PathNode) {
        text += pathNode.toString()
    }

    override fun visitTextArea(textAreaNode: TextAreaNode) {
        text += textAreaNode.toString()
    }

    override fun visitTitle(titleNode: TitleNode) {
        text += titleNode.toString()
    }

    override fun visitComment(cfwComment: CFWComment) {
        text += cfwComment.toString()
    }

    override fun visitA(aNode: ANode) {
        text += aNode.toString()
    }

    override fun visitEmpty(emptyNode: EmptyNode) {
        text += emptyNode.toString()
    }


}