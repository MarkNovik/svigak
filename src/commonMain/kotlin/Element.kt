package me.mark.svigak

/**
 * Represents inner svg element like Rect, Circle, etc.
 */
@SvgDsl
abstract class Element {
    //Exists for specifying untyped attributes
    //Typed attributes of child elements override this map
    val attributes: Attributes = mutableMapOf()
    var pathLength: Double? by attributes.nullable()
    var fill: Color? by attributes.nullable()
    var stroke: Color? by attributes.nullable()
    val id by attributes(super.hashCode())

    protected fun StringBuilder.appendAttributes(): StringBuilder {
        attributes
            .filterValues { it != null }
            .forEach { (k, v) -> append("$k=\"$v\" ") }
        return this
    }

    /**
     * @return String represenration of a tag. Must be a valid SVG
     */
    abstract override fun toString(): String
}

@SvgDsl
abstract class ContainerElement : Element() {
    var content = ""
}

@SvgDsl
class Rect : Element() {
    var x: Measure by attributes(0.px)
    var y: Measure by attributes(0.px)
    var width: Measure? by attributes.nullable()
    var height: Measure? by attributes.nullable()
    var rx: Measure? by attributes.nullable()
    var ry: Measure? by attributes.nullable()

    override fun toString(): String = buildFlatTag("rect") { appendAttributes() }
}

@SvgDsl
class Circle : Element() {
    var cx: Measure by attributes(0.px)
    var cy: Measure by attributes(0.px)
    var r: Measure by attributes(0.px)

    override fun toString(): String = buildFlatTag("circle") { appendAttributes() }
}

@SvgDsl
class Use : Element() {
    var x: Measure by attributes(0.px)
    var y: Measure by attributes(0.px)
    var width: Measure by attributes(0.px)
    var height: Measure by attributes(0.px)

    var href: String? by attributes.nullable()

    override fun toString(): String = buildFlatTag("use") { appendAttributes() }
}