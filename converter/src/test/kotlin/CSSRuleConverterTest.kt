import de.jensklingenberg.htmltocfw.converter.htmlToCompose
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CSSRuleConverterTest {


    @Test
    fun keyFrameTest() {
        val htmlSource = """<!DOCTYPE html>
<html>
<head>
<style> 
div {
  width: 100px;
  height: 100px;
  background: red;
  position: relative;
  animation: mymove 5s infinite;
}

@keyframes mymove {
  from {top: 0px;}
  to {top: 200px;}
}
</style>
</head>
<body>
</body>
</html>
"""

        val exp = """
@Composable
fun GeneratedComposable(){
 fun appStylesheet() = object : StyleSheet() {
val mymove by keyframes {
from {
top(0.px)
}
to {
top(200.px)
}
}
init {
"div" style { 
width(100.px)
height(100.px)
property("background","red")
position(Position.Relative)
animation(mymove){
duration(5.s)
iterationCount = listOf(null)}
}
}}
 
Style(appStylesheet())
}

            """.trimIndent()

        val actual = htmlToCompose(htmlSource)

        assertEquals(exp, actual)
    }


    @Test
    fun mediaRuleTest() {
        val htmlSource = """<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
body {
  background-color: yellow;
}

@media only screen and (max-width: 600px) {
  body {
    background-color: lightblue;
  }
}
</style>
</head>
<body>
</body>
</html>

"""

        val exp = """
@Composable
fun GeneratedComposable(){
 fun appStylesheet() = object : StyleSheet() {
init {
"body" style { 
property("background-color","yellow")
}
media("only screen and (max-width:600px)") {
"body" style { 
property("background-color","lightblue")
}
}
}}
 
Style(appStylesheet())
}

""".trimIndent()

        val actual = htmlToCompose(htmlSource)

        assertEquals(exp, actual)
    }

    @Test
    fun fontFaceRuleTest() {
        val htmlSource = """<!DOCTYPE html>
<html>
<head>
<style> 
@font-face {
  font-family: myFirstFont;
  src: url(sansation_light.woff);
}

div {
  font-family: myFirstFont;
}
</style>
</head>
<body>

<h1>The @font-face Rule</h1>

<div>With CSS, websites can finally use fonts other than the pre selected "web-safe" fonts.</div>

</body>
</html>"""

        val exp = """
@Composable
fun GeneratedComposable(){
 fun appStylesheet() = object : StyleSheet() {
fun fontFace() {
val newStyle = document.getElementsByTagName("style")[0] ?: document.createElement("style")

newStyle.append(
document.createTextNode(""${'"'}@font-face {
font-family:myFirstFont;
src:url(sansation_light.woff);
}
""${'"'})
)

document.head?.appendChild(newStyle);
}
init {
fontFace()
"div" style { 
property("font-family","myFirstFont")
}
}}
 
Style(appStylesheet())
H1 {
Text("The @font-face Rule")

}
Div {
Text("With CSS, websites can finally use fonts other than the pre selected \"web-safe\" fonts.")

}
}

""".trimIndent()

        val actual = htmlToCompose(htmlSource)

        assertEquals(exp, actual)
    }


}