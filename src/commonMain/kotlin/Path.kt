package me.mark.svigak

@SvgDsl
class Path : Element() {
    private val data = StringBuilder()
    var d: String
        get() = data.toString()
        set(value) {
            data.clear()
            data += value
        }

    fun moveTo(x: Number, y: Number) {
        data += "M $x $y "
    }

    fun move(dx: Number, dy: Number) {
        data += "m $dx $dy "
    }

    fun lineTo(x: Number, y: Number) {
        data += "L $x $y "
    }

    fun line(length: Number, height: Number) {
        data += "l $length $height "
    }

    fun horizontalTo(x: Number) {
        data += "H $x "
    }

    fun horizontal(length: Number) {
        data += "h $length "
    }

    fun verticalTo(y: Number) {
        data += "H $y "
    }

    fun vertical(height: Number) {
        data += "h $height "
    }

    fun custom(letter: Char, vararg numbers: Number) {
        data += "$letter ${numbers.asIterable().chunked(2) { it.joinToString(" ") }.joinToString(", ")} "
    }

    fun close() {
        data += "Z "
    }

    fun cubicCurveTo(
        control1X: Number,
        control1Y: Number,
        control2X: Number,
        control2Y: Number,
        destX: Number,
        destY: Number,
        sequentialCurves: (CubicCurve.() -> Unit)? = null
    ) {
        data += "C $control1X $control1Y, $control2X $control2Y, $destX $destY "
        if (sequentialCurves != null) data += CubicCurve().apply(sequentialCurves).toString()
    }

    fun cubicCurve(
        control1DX: Number,
        control1DY: Number,
        control2DX: Number,
        control2DY: Number,
        destDX: Number,
        destDY: Number,
        sequentialCurves: (CubicCurve.() -> Unit)? = null
    ) {
        data += "c $control1DX $control1DY, $control2DX $control2DY, $destDX $destDY "
        if (sequentialCurves != null) data += CubicCurve().apply(sequentialCurves).toString()
    }

    fun quadraticCurveTo(
        controlX: Number,
        controlY: Number,
        destX: Number,
        destY: Number,
        sequentialCurves: (QuadraticCurve.() -> Unit)? = null
    ) {
        data += "Q $controlX $controlY, $destX $destY "
        if (sequentialCurves != null) data += QuadraticCurve().apply(sequentialCurves).toString()
    }

    fun quadraticCurve(
        controlDX: Number,
        controlDY: Number,
        destDX: Number,
        destDY: Number,
        sequentialCurves: (QuadraticCurve.() -> Unit)? = null
    ) {
        data += "q $controlDX $controlDY, $destDX $destDY "
        if (sequentialCurves != null) data += QuadraticCurve().apply(sequentialCurves).toString()
    }

    override fun toString(): String = buildFlatTag("path") {
        attributes["d"] = data.toString()
        appendAttributes()
    }

    @SvgDsl
    class CubicCurve {
        private val data = StringBuilder()
        fun sequential(
            control2DX: Number,
            control2DY: Number,
            destDX: Number,
            destDY: Number
        ) {
            data += "s $control2DX $control2DY, $destDX $destDY "
        }

        fun sequentialTo(
            control2X: Number,
            control2Y: Number,
            destX: Number,
            destY: Number
        ) {
            data += "S $control2X $control2Y, $destX $destY "
        }

        override fun toString(): String = data.toString()
    }

    @SvgDsl
    class QuadraticCurve {
        private val data = StringBuilder()
        fun sequential(
            destDX: Number,
            destDY: Number
        ) {
            data += "t $destDX $destDY "
        }

        fun sequentialTo(
            destX: Number,
            destY: Number
        ) {
            data += "T $destX $destY "
        }

        override fun toString(): String = data.toString()
    }
}