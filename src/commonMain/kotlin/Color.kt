package me.mark.svigak

import kotlin.jvm.JvmInline

sealed interface Color

enum class W3CColor : Color {
    aqua, black, blue, fuchsia, gray, green, lime, maroon, navy, olive, purple, red, silver, teal, white, yellow;

    override fun toString(): String = this.name
}

@JvmInline
value class Custom(private val str: String) : Color {
    override fun toString(): String = str
}