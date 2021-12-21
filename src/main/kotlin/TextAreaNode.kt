import org.jsoup.nodes.Element

class TextAreaNode(private val element: Element) : MyNode {

    override fun print(): String {
        var str = "TextArea("
        str += printAttributes(element.attributes().asList())
        return "$str)"
    }

}