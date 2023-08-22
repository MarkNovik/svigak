package me.mark.svigak

class Measure(val length: Number, val unit: MeasureUnit) {
    override fun toString(): String = "$length$unit"
}

enum class MeasureUnit(private val str: String) {
    Pixel(""),
    Em("em"),
    Percent("%");

    override fun toString(): String = str
}

inline val <T : Number> T.px get() = Measure(this, MeasureUnit.Pixel)
inline val <T : Number> T.pct get() = Measure(this, MeasureUnit.Percent)
inline val <T : Number> T.em get() = Measure(this, MeasureUnit.Em)