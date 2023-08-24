@file:Suppress(
    "unused",
    "MemberVisibilityCanBePrivate"
) // Suppress all warnings for properties, because it is a library

package me.mark.svigak

import me.mark.svigak.AnimationAccumulate.none
import me.mark.svigak.AnimationAdditive.replace
import me.mark.svigak.AnimationCalcMode.linear
import me.mark.svigak.AnimationDuration.ClockValue
import me.mark.svigak.AnimationFill.remove
import kotlin.time.Duration

@AnimationDsl
class Animation<T : AnimatableValue>(
    name: String,
) {
    val attributes = Attributes()

    private val attributeName by attributes(name)

    var dur: AnimationDuration by attributes.lazy(indefinite)
    var min: ClockValue by attributes.lazy(Duration.ZERO.clock())
    var max: ClockValue? by attributes.nullable()
    var restart: AnimationRestart by attributes.lazy(AnimationRestart.always)
    var repeatCount: AnimationRepeatCount by attributes.lazy(indefinite)
    var repeatDur: AnimationDuration by attributes.lazy(indefinite)
    var fill: AnimationFill by attributes.lazy(remove)
    var calcMode: AnimationCalcMode by attributes.lazy(linear)
    var values = emptyList<T>()
    var keyTimes = emptyList<Number>()
    var keySplines: List<AnimationControlPoint> = emptyList()
    var from: T? by attributes.nullable()
    var to: T? by attributes.nullable()
    var by: T? by attributes.nullable()
    var additive: AnimationAdditive by attributes.lazy(replace)
    var accumulate: AnimationAccumulate by attributes.lazy(none)

    override fun toString(): String = buildFlatTag("animate") {
        append(
            attributes.joinToString(" ") { (k, v) ->
                "$k=\"$v\""
            }
        )
        if (values.isNotEmpty())
            append(
                values.joinToString(
                    prefix = " values=\"",
                    separator = ";",
                    postfix = "\" "
                )
            )
        if (keyTimes.isNotEmpty())
            append(
                keyTimes.joinToString(
                    prefix = " keyTimes=\"",
                    separator = ";",
                    postfix = "\" "
                )
            )
    }
}