import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Element

class ImgNode(val element: Element) : MyNode {

    override fun print(): String {

        var str = "Img ("
        val altValue = element.attributes().get("alt")
        element.attributes().remove("alt")
        val srcValue = element.attributes().get("src")
        element.attributes().remove("src")

        val attributesList: MutableList<Attribute> = element.attributes().asList()

        val attrText = printAttributes(attributesList)
        str += (attrText)
        if (attrText.isNotBlank()) {
            str += (", ")
        }
        str += ("src = \"${srcValue}\"")
        str += (", alt = \"${altValue}\"")
        str += (")")

        return str
    }

}