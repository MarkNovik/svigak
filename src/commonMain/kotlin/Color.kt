package me.mark.svigak

sealed interface Color

enum class W3CColor : Color {
    aqua, black, blue, fuchsia, gray, green, lime, maroon, navy, olive, purple, red, silver, teal, white, yellow;

    override fun toString(): String = this.name
}