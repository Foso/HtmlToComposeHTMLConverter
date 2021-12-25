package de.jensklingenberg.htmltocfw.converter

enum class CFWAttributeNames(val htmlAttrName: String, val cfwAttrName: String) {
    BACKGROUNDIMAGE("background-image", "backgroundImage"),
    BORDERRADIUS("border-radius", "borderRadius"),
    BOXSIZING("box-sizing","boxSizing"),
    DISPLAY("display","display"),
    FLEXDIRECTION("flex-direction","flex-direction"),
    FLEXWRAP("flex-wrap","flex-wrap"),
    FONT("font", "font"),
    MARGIN("margin", "margin"),
    PADDING("padding", "padding"),

    FONTSIZE("font-size", "fontSize"),
    TEXTALIGN("text-align", "textAlign"),
    TEXTDECORATION("text-decoration", "textDecoration"),
    FONTFAMILY("font-family", "fontFamily"),
    HEIGHT("height","height"),
    WIDTH("width","width");
}