import org.jsoup.nodes.TextNode

class MyTextNode(private val textNode: TextNode) : MyNode {
    override fun print(): String {
        return if (textNode.text().isNotBlank()) {
            val text = textNode.text().replace("\"", "\\\"")
            ("Text(\"${text}\")")
        } else {
            ""
        }
    }

}