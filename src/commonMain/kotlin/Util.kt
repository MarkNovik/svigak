package me.mark.svigak

import kotlin.reflect.KProperty0

internal fun StringBuilder.appendln(obj: Any? = null): StringBuilder {
    if (obj != null) append(obj)
    return append('\n')
}

internal fun flatTag(name: String, build: StringBuilder.() -> Unit = {}): String = buildString {
    append("<$name ")
    build()
    append("/>")
}

internal inline fun <T : Any> StringBuilder.appendIfPresent(p: KProperty0<T?>): StringBuilder {
    val res = p.get()
    val name = p.name
    return if (res != null) append("$name=\"$res\" ")
    else this
}

internal inline fun StringBuilder.appendProperty(p: KProperty0<Any?>): StringBuilder {
    return append("${p.name}=\"${p.get()}\" ")
}