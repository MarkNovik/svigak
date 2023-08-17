package me.mark.svigak

sealed interface Fill

//define set of colors allowed to be filled in
enum class W3CColor : Fill {
    aqua, black, blue, fuchsia, gray, green, lime, maroon, navy, olive, purple, red, silver, teal, white, yellow;

    override fun toString(): String = this.name
}