import org.jsoup.nodes.Element

class GenericNode(private val element: Element) : MyNode {

    override fun print(): String {
        var str = "\n"+element.tag().toString().capitalize() + " "

        val attrs = printAttributes(element.attributes().asList())
        if (attrs.isNotBlank()) {
            str += "($attrs)"
        }


        val childNodesText = element.childNodes().joinToString(separator = "") {
            getMyNode(it).print()
        }

        str += "{"
        if(childNodesText.isNotBlank()){
            str += "\n"
        }
        str += childNodesText
        if(childNodesText.isNotBlank()){
            str += "\n"
        }
        str += ("}\n")
        return str
    }
}