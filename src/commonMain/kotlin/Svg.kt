package me.mark.svigak

@DslMarker
annotation class SvgDsl

typealias Attributes = MutableMap<String, Any?>

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

inline fun Svg.rect(build: Rect.() -> Unit): Rect = Rect().apply(build).also(children::add)
inline fun Svg.circle(build: Circle.() -> Unit): Circle = Circle().apply(build).also(children::add)
inline fun Svg.path(build: Path.() -> Unit): Path = Path().apply(build).also(children::add)
inline fun Svg.use(elem: Element? = null, build: Use.() -> Unit): Use {
    val use = Use()
    if (elem != null) use.attributes["href"] = "#${elem.id}"
    use.build()
    children += use
    return use
}

/**
 * Creates an anonymous [Element] object and exposes it as receiver in [build].
 * You can define statically typed properties with [Element.attribute].
 */
fun Svg.flatTag(name: String, build: Element.() -> Unit): Element =
    object : Element() {
        override fun toString(): String = buildFlatTag(name) { appendAttributes() }

        init {
            build()
        }
    }.also(children::add)

fun Svg.containerTag(name: String, build: ContainerElement.() -> Unit): ContainerElement =
    object : ContainerElement() {
        override fun toString(): String = buildContainerTag(
            name,
            props = { appendAttributes() },
            content = { append(content) }
        )

        init {
            build()
        }
    }.also(children::add)



// Allows using custom defined elements with dsl
inline fun <T : Element> Svg.add(child: T, build: T.() -> Unit): T = child.apply(build).also(children::add)