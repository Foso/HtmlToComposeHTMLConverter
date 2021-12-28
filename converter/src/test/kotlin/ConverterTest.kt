import de.jensklingenberg.htmltocfw.converter.htmlToCompose
import de.jensklingenberg.htmltocfw.converter.node.GenericNode
import de.jensklingenberg.htmltocfw.converter.node.LabelNode
import de.jensklingenberg.htmltocfw.converter.node.getMyNode
import org.jsoup.Jsoup
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ConverterTest {

    @Test
    fun ButtonTest() {
        val doc = Jsoup.parse("""<button type="button" onclick="alert('Hello world!')">Click Me!</button>""");
        val exp = """
            Button (attrs = {
            attr("type","button")
            attr("onclick","alert('Hello world!')")
            }){
            Text("Click Me!")
            
            }
            
            """.trimIndent()

        val text = doc.body().childNodes().joinToString(separator = "") {
            getMyNode(it).toString()
        }

        assertEquals(exp, text)
    }

    @Test
    fun ImgTest() {
        val doc = Jsoup.parse("""<img src="img_girl.jpg" alt="Girl in a jacket" width="500" height="600">""");
        val exp = """
            Img (attrs = {
            attr("width","500")
            attr("height","600")
            }, src = "img_girl.jpg", alt = "Girl in a jacket")
            
            """.trimIndent()

        val text = doc.body().childNodes().joinToString(separator = "") {
            getMyNode(it).toString()
        }

        assertEquals(exp, text)
    }

    @Test
    fun nodeATest() {
        val html = """<a href="https://www.w3schools.com">Visit W3Schools.com!</a> """
        val exp = """
            A (href = "https://www.w3schools.com"){
            Text("Visit W3Schools.com!")
            
            }
            
            """.trimIndent()

        val text = htmlToCompose(html)

        assertEquals(exp, text)
    }

    @Test
    fun LabelTest() {
        val exp = """
            Label (forId = "testId"){
            Img (src = "testSrc", alt = "testAlt")
            }
            
            """.trimIndent()

        val labelAttributes = object : Attributes() {
            init {
                put(Attribute("for", "testId"))
            }
        }

        val testImg = Element("img").apply {
            val testImgAttributes = object : Attributes() {
                init {
                    put(Attribute("src", "testSrc"))
                    put(Attribute("alt", "testAlt"))
                }
            }
            attributes().addAll(testImgAttributes)
        }


        val text = LabelNode(labelAttributes, mutableListOf(testImg)).toString()

        assertEquals(exp, text)
    }

    @Test
    fun ButtonLabelTest2() {
        val exp = """
            Button {
            Text("Hello World")
            
            }
            
            """.trimIndent()

        val buttonAttributes = object : Attributes() {}

        val text = GenericNode("button", mutableListOf(TextNode("Hello World")), buttonAttributes).toString()

        assertEquals(exp, text)
    }

    @Test
    fun failedTest() {
        val html = """ <a href="https://www.w3schools.com">Visit W3Schools.com!</a> """
        val exp = """
            A (href = "https://www.w3schools.com"){
            Text("Visit W3Schools.com!")
            
            }
            
            """.trimIndent()

        val text = htmlToCompose(html)

        assertEquals(exp, text)
    }
}