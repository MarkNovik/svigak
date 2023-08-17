package me.mark.svigak

interface Element

class Rect : Element {
    var width: Size = 0.px
    var height: Size = 0.px
    var fill: Fill? = null

    private fun fill(): String = fill?.let { "fill=\"${fill!!}\"" } ?: ""
    override fun toString(): String =
        """<rect width="$width" height="$height" ${fill()} />"""
}