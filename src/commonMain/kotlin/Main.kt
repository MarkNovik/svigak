package me.mark.svigak

class Svg(val width: Measure, val height: Measure) {
    val children = mutableListOf<Element>()
    override fun toString(): String = buildString {
        appendln("""<svg width="$width" height="$height" xmlns="http://www.w3.org/2000/svg">""")
        children.forEach(::appendln)
        append("</svg>")
    }
}

fun svg(width: Measure, height: Measure, build: Svg.() -> Unit): Svg = Svg(width, height).apply(build)
inline fun Svg.rect(build: Rect.() -> Unit) {
    children += Rect().apply(build)
}

inline fun Svg.circle(build: Circle.() -> Unit) {
    children += Circle().apply(build)
}

fun Svg.elem(name: String, build: Element.() -> Unit) {
    children += object : Element() {
        override fun toString(): String = flatTag(name) {
            appendCommon()
        }

        init {
            apply(build)
        }
    }
}

inline fun <T : Element> Svg.add(child: T, build: T.() -> Unit) {
    children += child.apply(build)
}