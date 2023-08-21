package me.mark.svigak

@DslMarker
annotation class SvgDsl

typealias Attributes = MutableMap<String, Any>

@SvgDsl
class Svg(val width: Measure, val height: Measure) {
    val children = mutableListOf<Element>()
    override fun toString(): String = buildString {
        appendln("""<svg width="$width" height="$height" xmlns="http://www.w3.org/2000/svg">""")
        children.forEach(::appendln)
        append("</svg>")
    }
}

fun svg(width: Measure, height: Measure = width, build: Svg.() -> Unit): Svg = Svg(width, height).apply(build)
inline fun Svg.rect(build: Rect.() -> Unit) {
    children += Rect().apply(build)
}

inline fun Svg.circle(build: Circle.() -> Unit) {
    children += Circle().apply(build)
}

inline fun Svg.path(build: Path.() -> Unit) {
    children += Path().apply(build)
}

/**
 * Creates an anonymous [Element] object and exposes it as receiver in [build].
 * You can define statically typed properties with [Element.attribute].
 */
fun Svg.elem(name: String, build: Element.() -> Unit) {
    children += object : Element() {
        override fun toString(): String = flatTag(name) { appendCommon() }

        init {
            apply(build)
        }
    }
}

// Allows using custom defined elements with dsl
inline fun <T : Element> Svg.add(child: T, build: T.() -> Unit) {
    children += child.apply(build)
}