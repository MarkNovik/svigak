package me.mark.svigak

@AnimationDsl
class Animation<T>(
    name: String,
) {
    val attributes = Attributes()
    private val name by attributes(name, "attributeName")
    var dur: String by attributes("") // TODO: It's only for prototyping purposes, i swear (ClockValue)
    var values = mutableListOf<T>()

    override fun toString(): String = buildFlatTag("animate") {
        append(
            attributes.joinToString(" ") { (k, v) ->
                "$k=\"$v\""
            }
        )
        append(
            values.joinToString(
                prefix = " values=\"",
                separator = ";",
                postfix = "\" "
            )
        )
    }
}