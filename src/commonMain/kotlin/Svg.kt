package me.mark.svigak

@DslMarker
annotation class SvgDsl

interface ElementContainer {
    val children: MutableList<Element>
}

@SvgDsl
class Svg(val width: Measure, val height: Measure) : ElementContainer {
    override val children = mutableListOf<Element>()
    override fun toString(): String = buildString {
        appendln("""<svg width="$width" height="$height" xmlns="http://www.w3.org/2000/svg">""")
        children.forEach(::appendln)
        append("</svg>")
    }
}

fun svg(width: Measure, height: Measure = width, build: Svg.() -> Unit): Svg = Svg(width, height).apply(build)

inline fun ElementContainer.rect(build: Rect.() -> Unit): Rect = Rect().apply(build).also(children::add)
inline fun ElementContainer.circle(build: Circle.() -> Unit): Circle = Circle().apply(build).also(children::add)
inline fun ElementContainer.path(build: Path.() -> Unit): Path = Path().apply(build).also(children::add)
inline fun ElementContainer.g(build: G.() -> Unit): G = G().apply(build).also(children::add)
inline fun ElementContainer.use(elem: Element, build: Use.() -> Unit): Use {
    val use = Use(elem)
    use.build()
    children += use
    return use
}

fun ElementContainer.text(text: String = "", build: Text.() -> Unit): Text = Text(text).apply(build).also(children::add)

/**
 * Creates an anonymous [Element] object and exposes it as receiver in [build].
 * You can define statically typed properties with [Attributes.invoke] or [Attributes.nullable].
 */
fun ElementContainer.flatTag(name: String, build: Element.() -> Unit): Element =
    object : Element() {
        override fun toString(): String = buildFlatTag(name) { appendAttributes() }

        init {
            build()
        }
    }.also(children::add)


// Allows using custom defined elements with dsl
inline fun <T : Element> ElementContainer.add(child: T, build: T.() -> Unit): T = child.apply(build).also(children::add)