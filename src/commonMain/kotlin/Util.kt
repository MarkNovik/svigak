package me.mark.svigak

internal operator fun StringBuilder.plusAssign(str: String) {
    append(str)
}

internal fun StringBuilder.appendln(obj: Any? = null): StringBuilder {
    if (obj != null) append(obj)
    return append('\n')
}

internal fun buildFlatTag(name: String, build: (StringBuilder.() -> Unit)? = null): String = buildString {
    append("<$name ")
    build?.let { it() }
    append("/>")
}

internal fun buildContainerTag(name: String, props: (StringBuilder.() -> Unit)? = null, content: (StringBuilder.() -> Unit)? = null): String = buildString {
    append("<$name ")
    props?.let { it() }
    append(" >")
    content?.let { it() }
    append("</$name>")
}