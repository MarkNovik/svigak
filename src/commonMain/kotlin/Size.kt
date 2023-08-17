package me.mark.svigak

class Size(val size: Double, val units: SizeUnit) {
    override fun toString(): String = "$size$units"
}

enum class SizeUnit(private val str: String) {
    Pixels(""),
    Percent("%");

    override fun toString(): String = str
}

val Number.px get() = Size(toDouble(), SizeUnit.Pixels)
val Number.pct get() = Size(toDouble(), SizeUnit.Percent)