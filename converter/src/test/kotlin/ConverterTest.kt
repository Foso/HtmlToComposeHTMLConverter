import de.jensklingenberg.htmltocfw.converter.htmlToCompose
import de.jensklingenberg.htmltocfw.converter.node.GenericNode
import de.jensklingenberg.htmltocfw.converter.node.LabelNode
import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Attributes
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ConverterTest {

    @Test
    fun labelAndImgTest() {
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
    fun buttonTest() {
        val exp = """
            Button (attrs = {
            attr("type","button")
            attr("onclick","alert('Hello world!')")
            }){
            Text("Hello World")
            
            }
            
            """.trimIndent()

        val buttonAttributes = object : Attributes() {
            init {
                put(Attribute("type", "button"))
                put(Attribute("onclick", "alert('Hello world!')"))
            }
        }

        val text = GenericNode("button", mutableListOf(TextNode("Hello World")), buttonAttributes).toString()

        assertEquals(exp, text)
    }

    @Test
    fun ANodeTest() {
        val exp = """
            A (attrs = {
            attr("href","www.example.com")
            }){
            Text("Hello World")
            
            }

            """.trimIndent()

        val buttonAttributes = object : Attributes() {
            init {
                put(Attribute("href", "www.example.com"))
            }
        }

        val text = GenericNode("a", mutableListOf(TextNode("Hello World")), buttonAttributes).toString()

        assertEquals(exp, text)
    }

    @Test
    fun parserTest() {
        val sourceHtml = wholeSite
        val expected = expectedCode
        val text = htmlToCompose(sourceHtml)

        assertEquals(expected, text)
    }
}