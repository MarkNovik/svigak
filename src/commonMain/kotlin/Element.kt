@file:Suppress("unused")

package me.mark.svigak

import kotlin.reflect.KProperty0

/**
 * Represents inner svg element like Rect, Circle, etc.
 */
@SvgDsl
@AnimationDsl
abstract class Element(private val name: String) {
    //Exists for specifying untyped attributes
    //Typed attributes of child elements override this map
    val attributes: Attributes = Attributes()
    var pathLength: Double? by attributes.nullable()
    var fill: Color? by attributes.nullable()
    var stroke: Color? by attributes.nullable()
    val id by attributes(super.hashCode())
    private val animations: MutableList<Animation<*>> = mutableListOf()

    fun <T> animate(attribute: String, animation: Animation<T>.() -> Unit) {
        val x = Animation<T>(attribute).apply(animation)
        animations.add(x)
    }

    fun <T> KProperty0<T?>.animate(animation: Animation<T>.() -> Unit) = animate(this@animate.name, animation)

    protected fun StringBuilder.appendAttributes(): StringBuilder {
        attributes
            .forEach { (k, v) -> append("$k=\"$v\" ") }
        return this
    }

    private fun StringBuilder.appendAnimations(): StringBuilder {
        animations.forEach { appendln(it) }
        return this
    }

    /**
     * @return String represenration of a tag. Must be a valid SVG
     */
    override fun toString(): String = when {
        animations.isEmpty() -> buildFlatTag(name) { appendAttributes() }
        else -> buildContainerTag(
            name,
            props = { appendAttributes() },
            content = {
                appendln()
                appendAnimations()
            }
        )
    }
}

@SvgDsl
class Text(initText: String = "") : Element("text") {
    var content = initText

    override fun toString(): String = buildContainerTag(
        "text",
        props = { appendAttributes() },
        content = { append(content) }
    )
}

@SvgDsl
class Rect : Element("rect") {
    var x: Measure by attributes.lazy(0.px)
    var y: Measure by attributes.lazy(0.px)
    var width: Measure? by attributes.nullable()
    var height: Measure? by attributes.nullable()
    var rx: Measure? by attributes.nullable()
    var ry: Measure? by attributes.nullable()
}

@SvgDsl
class Circle : Element("circle") {
    var cx: Measure by attributes.lazy(0.px)
    var cy: Measure by attributes.lazy(0.px)
    var r: Measure by attributes.lazy(0.px)
}

@SvgDsl
class Use(href: Element) : Element("use") {
    var x: Measure by attributes.lazy(0.px)
    var y: Measure by attributes.lazy(0.px)
    var width: Measure by attributes.lazy(0.px)
    var height: Measure by attributes.lazy(0.px)

    var href: String by attributes("#${href.id}")
}

@SvgDsl
class G : Element("g"), ElementContainer {
    override val children: MutableList<Element> = mutableListOf()

    override fun toString(): String = buildContainerTag(
        "g",
        props = {
            appendAttributes()
        },
        content = {
            appendln()
            appendln(children.joinToString("\n"))
        }
    )
}