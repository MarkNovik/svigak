package me.mark.svigak

internal fun StringBuilder.appendln(obj: Any? = null): StringBuilder {
    if (obj != null) append(obj)
    return append('\n')
}