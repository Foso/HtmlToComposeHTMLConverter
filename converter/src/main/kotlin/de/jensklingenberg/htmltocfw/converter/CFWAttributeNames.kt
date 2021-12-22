package de.jensklingenberg.htmltocfw.converter

enum class CFWAttributeNames(val htmlAttrName: String, val cfwAttrName: String) {
    BACKGROUNDIMAGE("background-image", "backgroundImage"),
    BORDERRADIUS("border-radius", "borderRadius"),
    FONT("font", "font"),
    MARGIN("margin", "margin"),
    PADDING("padding", "padding"),
    FONTSIZE("font-size", "fontSize"),
    TEXTALIGN("text-align", "textAlign"),
    TEXTDECORATION("text-decoration", "textDecoration"),
    FONTFAMILY("font-family", "fontFamily");
}