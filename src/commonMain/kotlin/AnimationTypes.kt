@file:Suppress("ClassName", "EnumEntryName", "unused")

package me.mark.svigak

import kotlin.jvm.JvmInline
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class AnimationControlPoint(
    val x1: Double,
    val y1: Double,
    val x2: Double,
    val y2: Double,
) {
    init {
        require(
            x1 in 0.0..1.0 &&
                    y1 in 0.0..1.0 &&
                    x2 in 0.0..1.0 &&
                    y2 in 0.0..1.0
        ) {
            "all coordinates must be in range 0..1"
        }
    }

    override fun toString(): String =
        "$x1 $y1 $x2 $y2"
}

enum class AnimationAdditive {
    replace, sum
}

enum class AnimationAccumulate {
    none, sum
}

enum class AnimationRestart {
    always,
    whenNotActive,
    never
}

enum class AnimationFill {
    freeze,
    remove
}

enum class AnimationCalcMode {
    discrete, linear, paced, spline
}

data object indefinite : AnimationDuration, AnimationRepeatCount

sealed interface AnimationRepeatCount {
    @JvmInline
    value class Times(private val n: Number) {
        override fun toString(): String = n.toString()
    }
}

sealed interface AnimationDuration {
    data object media : AnimationDuration

    @JvmInline
    value class ClockValue(private val dur: Duration) : AnimationDuration {
        init {
            require(!dur.isNegative()) { "ClockValue must be non-negative" }
        }

        override fun toString(): String = buildString {
            dur.toComponents { hours, minutes, seconds, nanos ->
                if (hours != 0L) append("$hours:")
                append(minutes.toString().padStart(2, '0'))
                append(':')
                append(seconds.toString().padStart(2, '0'))
                if (nanos != 0) {
                    append(".")
                    append(nanos / 1_000_000.0)
                }
            }
        }
    }
}

fun Duration.clock() = AnimationDuration.ClockValue(this)
val Number.ms get() = toDouble().milliseconds.clock()
val Number.s get() = toDouble().seconds.clock()
val Number.min get() = toDouble().minutes.clock()
val Number.h get() = toDouble().hours.clock()

val Number.times get() = AnimationRepeatCount.Times(this)

fun cp(x1: Double, y1: Double, x2: Double, y2: Double) = AnimationControlPoint(x1, y1, x2, y2)
