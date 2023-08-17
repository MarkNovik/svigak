package me.mark.svigak

class Svg(val width: Size, val height: Size) {
    val children = mutableListOf<Element>()
    override fun toString(): String = buildString {
        appendln("""<svg width="$width" height="$height" xmlns="http://www.w3.org/2000/svg">""")
        children.forEach(::appendln)
        append("</svg>")
    }
}

fun svg(width: Size, height: Size, build: Svg.() -> Unit): Svg = Svg(width, height).apply(build)
fun Svg.rect(build: Rect.() -> Unit) {
    children += Rect().apply(build)
}