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

internal inline fun StringBuilder.appendIfPresent(p: KProperty0<Any?>): StringBuilder {
    val res = p.get()
    val name = p.name
    return if (res != null) append("$name=\"$res\" ")
    else this
}

internal inline fun StringBuilder.appendProperty(p: KProperty0<Any>): StringBuilder {
    return append("${p.name}=\"${p.get()}\" ")
}