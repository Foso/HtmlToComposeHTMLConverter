
rootProject.name = "HtmlToComposeWebConverter"

include(
    ":converter",

    ":ideaplugin"

)

pluginManagement {
    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
    }
}
