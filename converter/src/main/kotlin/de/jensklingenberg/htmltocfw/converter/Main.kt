package de.jensklingenberg.htmltocfw.converter

import de.jensklingenberg.htmltocfw.converter.node.getMyNode
import org.jsoup.Jsoup
import org.jsoup.parser.ParseSettings
import org.jsoup.parser.Parser
import java.io.File

fun main() {

    val html =
        File("/home/jens/Code/2021/jk/HtmlToComposeWeb/converter/src/main/kotlin/de/jensklingenberg/htmltocfw/converter/html.text").readText()

    val text = htmlToCompose(html)

    File("/home/jens/Code/2021/jk/HtmlToComposeWeb/converter/src/main/kotlin/de/jensklingenberg/htmltocfw/converter/Result.txt").writeText(
        text
    )
}

fun htmlToCompose(html: String): String {
    val  parser = Parser.htmlParser();
    parser.settings(ParseSettings(true, true))
    val doc = parser.parseInput(html,"")

    return doc.body().childNodes().joinToString(separator = "") {
        getMyNode(it).print()
    }
}


val String.isNumber: Boolean
    get() {
        return this.toIntOrNull() != null
    }

fun formatStringValue(text: String): String {
    return "\"${text}\""
}


