package de.jensklingenberg.htmltocfw.converter.node

import com.helger.commons.collection.impl.ICommonsList
import com.helger.css.ECSSVersion
import com.helger.css.decl.CSSStyleRule
import com.helger.css.decl.CascadingStyleSheet
import com.helger.css.reader.CSSReader
import de.jensklingenberg.htmltocfw.converter.parseStyleProperties
import org.jsoup.nodes.Element
import java.nio.charset.StandardCharsets

/**
 *
 */
fun parseStyleRules(styleRules: ICommonsList<CSSStyleRule>): String {
    var str = ""
    styleRules.forEach { styleRule ->
        val styleName = styleRule.allSelectors.joinToString { it.asCSSString }
        str += "\"$styleName\" style { \n"
        styleRule.allDeclarations.forEach { declaration ->
            val propName = declaration.property
            val propValue = declaration.expressionAsCSSString

            str += parseStyleProperties(propName, propValue)
            str += "\n"
        }
        str += "}\n"
    }
    return str
}


fun read(text: String): CascadingStyleSheet? {
    /**
     * I don't understand this issue, ECSSVersion.CSS30 can only be found by reflection
     * otherwise there is always a NoClassDefFoundException
     */
    val aClass = StyleNode::class.java.classLoader.loadClass("com.helger.css.ECSSVersion")
    val ter: ECSSVersion = aClass.enumConstants[2] as ECSSVersion
    return CSSReader.readFromString(text, StandardCharsets.UTF_8, ter);
}

/**
 * This will transform the style tag to a StyleSheet
 */
class StyleNode(private val htmlStyleNode: Element) : MyNode {

    override fun toString(): String {
        val hasMediaAttribute = htmlStyleNode.attributes().get("media")

        var str = "fun appStylesheet() = object : StyleSheet() {\n" + "init {\n"

        val aCSS =
            read(htmlStyleNode.data())

        aCSS?.allStyleRules?.let {
            str += if (hasMediaAttribute.isNotBlank()) {
                parseMediaQuery(hasMediaAttribute, it)
            } else {
                parseStyleRules(it)
            }
        }

        aCSS?.allMediaRules?.forEach { mediaRule ->
            val query = mediaRule.allMediaQueries.joinToString { it.asCSSString }

            mediaRule?.allStyleRules?.let { cssStyleRules ->
                str += parseMediaQuery(query, cssStyleRules)
            }
        }
        str += "}}\n"
        return str
    }
}


fun parseMediaQuery(query: String, cssStyleRules: ICommonsList<CSSStyleRule>): String {
    var str = "media(\"$query\") {\n"
    str += parseStyleRules(cssStyleRules)
    str += "}\n"
    return str
}