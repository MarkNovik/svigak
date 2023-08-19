package me.mark.svigak

import kotlin.reflect.KProperty0

internal operator fun StringBuilder.plusAssign(str: String) {
    append(str)
}

internal fun StringBuilder.appendln(obj: Any? = null): StringBuilder {
    if (obj != null) append(obj)
    return append('\n')
}

internal fun flatTag(name: String, build: (StringBuilder.() -> Unit)? = null): String = buildString {
    append("<$name ")
    build?.let { it() }
    append("/>")
}