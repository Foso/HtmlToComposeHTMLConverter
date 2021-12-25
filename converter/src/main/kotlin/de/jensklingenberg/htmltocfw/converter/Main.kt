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
        getMyNode(it).print()
    }

    val hasStyle = doc.head().allElements.any { it.tag().toString() == "style" }


    val body = doc.body().childNodes().joinToString(separator = "") {
        getMyNode(it).print()
    }

    val wrappedBody = if (hasStyle) {
        "@Composable fun HtmlComposable(){\nStyle(AppStylesheet)\n" + body + "}\n"
    } else {
        body
    }


    return head + wrappedBody
}


val String.isNumber: Boolean
    get() {
        return this.toIntOrNull() != null
    }

fun formatStringValue(text: String): String {
    return "\"${text}\""
}


