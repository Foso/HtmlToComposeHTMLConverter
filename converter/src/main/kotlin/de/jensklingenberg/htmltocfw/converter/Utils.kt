package de.jensklingenberg.htmltocfw.converter


//Left Compose names // Right Css Name
val unitsMap = mapOf(
    "number" to "number",
    "percent" to "%",
    "em" to "em",
    "ex" to "ex",
    "ch" to "ch",
    "cssRem" to "rem",
    "vw" to "vw",
    "vh" to "vh",
    "vmin" to "vmin",
    "vmax" to "vmax",
    "cm" to "cm",
    "mm" to "mm",
    "Q" to "Q",
    "pt" to "pt",
    "pc" to "pc",
    "px" to "px",
    "deg" to "deg",
    "grad" to "grad",
    "rad" to "rad",
    "turn" to "turn",
    "s" to "s",
    "ms" to "ms",
    "Hz" to "Hz",
    "kHz" to "kHz",
    "dpi" to "dpi",
    "dpcm" to "dpcm",
    "dppx" to "dppx",
    "fr" to "fr",
)


val String.isNumber: Boolean
    get() {
        return this.toIntOrNull() != null
    }

fun formatStringValue(text: String): String {
    return "\"${text}\""
}
