import org.jsoup.nodes.Comment

class MyComment(private val comment: Comment) : MyNode {
    override fun print(): String {
        return ("//" + comment.data)
    }

}