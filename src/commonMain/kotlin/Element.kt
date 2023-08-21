package me.mark.svigak

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

/**
 * Represents inner svg element like Rect, Circle, etc.
 */
@SvgDsl
abstract class Element {
    //Exists for specifying untyped attributes
    //Typed attributes of child elements override this map
    val attributes: Attributes = mutableMapOf()
    var pathLength: Double? = null
    var fill: Color? = null
    var stroke: Color? = null

    protected fun StringBuilder.appendCommon(): StringBuilder {
        appendIfPresent(::pathLength)
        appendIfPresent(::fill)
        appendIfPresent(::stroke)
        attributes.forEach { (k, v) ->
            append("$k=\"$v\" ")
        }
        return this
    }

    protected inline fun StringBuilder.appendIfPresent(p: KProperty0<Any?>): StringBuilder {
        val res = p.get() ?: attributes[p.name]
        val name = p.name
        return if (res != null) append("$name=\"$res\" ")
        else this
    }

    protected inline fun StringBuilder.appendProperty(p: KProperty0<Any>): StringBuilder {
        attributes -= p.name
        return append("${p.name}=\"${p.get()}\" ")
    }

    /**
     * @return String represenration of a tag. Must be a valid SVG
     */
    abstract override fun toString(): String
}

@SvgDsl
class Rect : Element() {
    var x: Measure = 0.px
    var y: Measure = 0.px
    var width: Measure? = null
    var height: Measure? = null
    var rx: Measure? = null
    var ry: Measure? = null

    override fun toString(): String = flatTag("rect") {
        appendProperty(::x)
        appendProperty(::y)
        appendIfPresent(::width)
        appendIfPresent(::height)
        appendIfPresent(::rx)
        appendIfPresent(::ry)
        appendCommon()
    }
}

@SvgDsl
class Circle : Element() {
    var cx: Measure = 0.px
    var cy: Measure = 0.px
    var r: Measure = 0.px

    override fun toString(): String = flatTag("circle") {
        appendProperty(::cx)
        appendProperty(::cy)
        appendProperty(::r)
        appendCommon()
    }
}

