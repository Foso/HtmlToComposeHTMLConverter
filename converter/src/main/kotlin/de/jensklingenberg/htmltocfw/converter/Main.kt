package de.jensklingenberg.htmltocfw.converter

import de.jensklingenberg.htmltocfw.converter.node.getMyNode
import org.jsoup.parser.ParseSettings
import org.jsoup.parser.Parser
import java.io.File

fun main() {

    val html =
        File("/home/jens/Code/2022/jk/HtmlToComposeWeb/converter/src/main/kotlin/de/jensklingenberg/htmltocfw/converter/html.text").readText()

    val text = htmlToCompose(html)

    File("/home/jens/Code/2022/jk/HtmlToComposeWeb/converter/src/main/kotlin/de/jensklingenberg/htmltocfw/converter/Result.txt").writeText(
        text
    )
}

fun htmlToCompose(html: String): String {

    val parser = Parser.htmlParser();
    parser.settings(ParseSettings(true, true))
    val doc = parser.parseInput(html, "")

    val head = doc.head().childNodes().joinToString("") { node ->
        getMyNode(node).toString()
    }

    val hasStyle = doc.head().allElements.any { it.tag().toString() == "style" }

    val body = doc.body().childNodes().joinToString("") { node ->
        getMyNode(node).toString()
    }

    val wrappedBody = if (hasStyle) {
        "Style(appStylesheet())\n$body"
    } else {
        body
    }


    return "@Composable\nfun GeneratedComposable(){\n $head \n$wrappedBody}\n"
}



