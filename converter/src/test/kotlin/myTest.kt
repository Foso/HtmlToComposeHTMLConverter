import de.jensklingenberg.htmltocfw.converter.getMyNode
import de.jensklingenberg.htmltocfw.converter.htmlToCompose
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class myTest {

    @Test
    fun ButtonTest(){
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
            getMyNode(it).print()
        }

        assertEquals(exp,text)
    }

    @Test
    fun ImgTest(){
        val doc = Jsoup.parse("""<img src="img_girl.jpg" alt="Girl in a jacket" width="500" height="600">""");
        val exp = """
            Img (attrs = {
            attr("width","500")
            attr("height","600")
            }, src = "img_girl.jpg", alt = "Girl in a jacket")
            """.trimIndent()

        val text = doc.body().childNodes().joinToString(separator = "") {
            getMyNode(it).print()
        }

        assertEquals(exp,text)
    }

    @Test
    fun nodeATest(){
        val html = """ <a href="https://www.w3schools.com">Visit W3Schools.com!</a> """
        val exp = """
            A (href = "https://www.w3schools.com"){
            Text("Visit W3Schools.com!")
            }
            
            """.trimIndent()

        val text = htmlToCompose(html)

        assertEquals(exp,text)
    }

    @Test
    fun failedTest(){
        val html = """ a href="https://www.w3schools.com<<">Visit W3Schools.com!</a> """
        val exp = """
            A (href = "https://www.w3schools.com"){
            Text("Visit W3Schools.com!")
            }
            
            """.trimIndent()

        val text = htmlToCompose(html)

        assertEquals(exp,text)
    }
}