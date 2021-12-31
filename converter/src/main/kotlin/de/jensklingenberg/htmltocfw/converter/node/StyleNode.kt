package de.jensklingenberg.htmltocfw.converter.node

import com.helger.commons.collection.impl.ICommonsList
import com.helger.css.ECSSVersion
import com.helger.css.decl.CSSKeyframesRule
import com.helger.css.decl.CSSStyleRule
import com.helger.css.decl.CascadingStyleSheet
import com.helger.css.reader.CSSReader
import de.jensklingenberg.htmltocfw.converter.parseStyleProperties
import org.jsoup.nodes.Element
import java.nio.charset.StandardCharsets


/**
 * This will transform the style tag to a StyleSheet
 */
class StyleNode(private val htmlStyleNode: Element) : MyNode {

    override fun toString(): String {
        val mediaAttribute = htmlStyleNode.attributes().get("media")
        val aCSS =
            parseCascadingStyleSheet(htmlStyleNode.data())

        val styleSheetName = "appStylesheet"

        var styleSheetBody = ""
        aCSS?.let {
            it.allKeyframesRules.forEach { keyFrameRule ->
                styleSheetBody += createKeyframe(keyFrameRule)
            }

            styleSheetBody += "init {\n"

            it.allStyleRules.let {
                styleSheetBody += if (mediaAttribute.isNotBlank()) {
                    createMediaRule(mediaAttribute, it)
                } else {
                    parseStyleRules(it)
                }
            }

            it.allMediaRules.forEach { mediaRule ->
                val query = mediaRule.allMediaQueries.joinToString { it.asCSSString }

                mediaRule?.allStyleRules?.let { cssStyleRules ->
                    styleSheetBody += createMediaRule(query, cssStyleRules)
                }
            }
            styleSheetBody += "}"
        }

        var str = "fun $styleSheetName() = object : StyleSheet() {\n"
        str += styleSheetBody
        str += "}\n"
        return str
    }

    private fun parseCascadingStyleSheet(text: String): CascadingStyleSheet? {
        /**
         * I don't understand this issue, ECSSVersion.CSS30 can only be found by reflection
         * otherwise there is always a NoClassDefFoundException
         */
        val aClass = StyleNode::class.java.classLoader.loadClass("com.helger.css.ECSSVersion")
        val ecssVersion: ECSSVersion = aClass.enumConstants[2] as ECSSVersion //CSS30
        return CSSReader.readFromString(text, StandardCharsets.UTF_8, ecssVersion);
    }

    private fun createKeyframe(keyFrameRule: CSSKeyframesRule): String {
        var str = "val " + keyFrameRule.animationName + " by keyframes {\n"

        keyFrameRule.allBlocks.forEach {
            val keyframeSelector = it.allKeyframesSelectors.joinToString { it }
            str += if (keyframeSelector.endsWith("%")) {
                val selectorWithPercent = keyframeSelector.replace("%", ".percent")
                "each(${selectorWithPercent}) {\n"
            } else {
                "$keyframeSelector {\n"
            }

            it.allDeclarations.forEach { cssDecl ->

                val propValue = cssDecl.expressionAsCSSString
                val propName = cssDecl.property

                str += when (propValue) {
                    "animation-name" -> {
                        val duration =
                            it.allDeclarations.find { it.property == "animation-duration" }?.expressionAsCSSString
                        val cssString = listOf(propValue, duration).joinToString(" ") { it.toString() }
                        parseStyleProperties("animation", cssString)
                    }
                    "animation-duration" -> {}
                    else -> {
                        parseStyleProperties(propName, propValue) + "\n"
                    }
                }

            }
            str += "}\n"
        }
        str += "}\n"
        return str
    }

    private fun createMediaRule(query: String, cssStyleRules: ICommonsList<CSSStyleRule>): String {
        val str = "media(\"$query\") {\n" + parseStyleRules(cssStyleRules) + "}\n"
        return str
    }

    /**
     *
     */
    private fun parseStyleRules(styleRules: ICommonsList<CSSStyleRule>): String {
        var str = ""
        styleRules.forEach { styleRule ->
            val styleName = styleRule.allSelectors.joinToString { it.asCSSString }

            str += "\"$styleName\" style { \n"

            styleRule.allDeclarations.forEach { declaration ->
                val propName = declaration.property
                val propValue = declaration.expressionAsCSSString

                str += when (propName) {
                    "animation-name" -> {
                        val duration =
                            styleRule.allDeclarations.find { it.property == "animation-duration" }?.expressionAsCSSString

                        val cssString = listOf(propValue, duration).joinToString(" ") { it.toString() }
                        parseStyleProperties("animation", cssString)
                    }

                    else -> {
                        parseStyleProperties(propName, propValue)
                    }
                }

                str += "\n"
            }
            str += "}\n"
        }
        return str
    }
}

