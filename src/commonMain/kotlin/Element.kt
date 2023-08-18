package me.mark.svigak

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Represents inner svg element like Rect, Circle, etc.
 */
abstract class Element {
    //Exists for specifying untyped attributes
    //Typed attributes of child elements override this map
    val attributes: MutableMap<String, String> = mutableMapOf()
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

    /**
     * @return String represenration of a tag. Must be a valid SVG
     */
    abstract override fun toString(): String


    /**
     * Returns a delegate-able [ReadWriteProperty] that uses its name as key in [attributes] and stores its value there.
     * Intended to be used in [elem].
     */
    fun <THIS, T> attribute(initValue: T, toString: (T) -> String = { it.toString() }) =
        object : ReadWriteProperty<THIS, T> {
            private var value = initValue

            override fun getValue(thisRef: THIS, property: KProperty<*>): T {
                if (property.name !in attributes) attributes[property.name] = toString(value)
                return value
            }

            override fun setValue(thisRef: THIS, property: KProperty<*>, value: T) {
                this.value = value
                attributes[property.name] = value.toString()
            }
        }
}

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

