import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element

class FormNode(private val element: Element) : MyNode {

    override fun print(): String {

        var str = "Form ("
        val actionValue = element.attributes().get("action")
        element.attributes().remove("action")

        val attributesList: MutableList<Attribute> = element.attributes().asList()

        val attrText = printAttributes(attributesList)
        str += attrText

        if (actionValue.isNotBlank()) {
            if (attrText.isNotBlank()) {
                str += (", ")
            }
        }
        str += ("action = \"${actionValue}\"")
        str += (")")

        val childNodesText = element.childNodes().joinToString(separator = "") {
            getMyNode(it).print()
        }

        str += "{"
        if (childNodesText.isNotBlank()) {
            str += "\n"
        }
        str += childNodesText
        if (childNodesText.isNotBlank()) {
            str += "\n"
        }
        str += ("}\n")
        return str
    }

}