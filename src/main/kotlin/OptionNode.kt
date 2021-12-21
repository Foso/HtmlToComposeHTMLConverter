import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element

class OptionNode(private val element: Element) : MyNode {

    override fun print(): String {

        var str = "Option ("
        val valueValue = element.attributes().get("value")
        element.attributes().remove("value")

        val attributesList: MutableList<Attribute> = element.attributes().asList()

        val attrText = printAttributes(attributesList)
        str += (attrText)

        if (attrText.isNotBlank()) {
            str += (",")
        }
        str += ("value = \"${valueValue}\"")
        str += (")")

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