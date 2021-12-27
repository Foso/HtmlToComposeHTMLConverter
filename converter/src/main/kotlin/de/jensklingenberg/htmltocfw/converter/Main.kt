package de.jensklingenberg.htmltocfw.converter

import de.jensklingenberg.htmltocfw.converter.node.getMyNode
import org.jsoup.parser.ParseSettings
import org.jsoup.parser.Parser
import java.io.File

fun main() {

    val html =
        File("/Users/jklingenberg/Code/2021/jk/HtmlToComposeWebConverter/converter/src/main/kotlin/de/jensklingenberg/htmltocfw/converter/html.text").readText()

    val text = htmlToCompose(html)

    File("/Users/jklingenberg/Code/2021/jk/HtmlToComposeWebConverter/converter/src/main/kotlin/de/jensklingenberg/htmltocfw/converter/Result.txt").writeText(
        text
    )
}

fun htmlToCompose(html: String): String {

    val parser = Parser.htmlParser();
    parser.settings(ParseSettings(true, true))
    val doc = parser.parseInput(html, "")

    val head = doc.head().childNodes().joinToString(separator = "") {
        getMyNode(it).toString()
    }

    val hasStyle = doc.head().allElements.any { it.tag().toString() == "style" }


    val body = doc.body().childNodes().joinToString(separator = "") {
        getMyNode(it).toString()
    }

    val wrappedBody = if (hasStyle) {
        "Style(appStylesheet())\n$body"
    } else {
        body
    }


    return "@Composable\nfun GeneratedComposable(){\n $head \n$wrappedBody}\n"
}



