package me.mark.svigak

class Path : Element() {
    private val data = StringBuilder()
    var d: String
        get() = data.toString()
        set(value) {
            data.clear()
            data += value
        }

    fun moveTo(x: Double, y: Double) {
        data += "M $x $y "
    }

    fun move(dx: Double, dy: Double) {
        data += "m $dx $dy "
    }

    fun lineTo(x: Double, y: Double) {
        data += "L $x $y "
    }

    fun line(length: Double, height: Double) {
        data += "l $length $height "
    }

    fun horizontalTo(x: Double) {
        data += "H $x "
    }

    fun horizontal(length: Double) {
        data += "h $length "
    }

    fun verticalTo(y: Double) {
        data += "H $y "
    }

    fun vertical(height: Double) {
        data += "h $height "
    }

    fun close() {
        data += "Z "
    }

    fun cubicCurveTo(
        control1X: Double,
        control1Y: Double,
        control2X: Double,
        control2Y: Double,
        destX: Double,
        destY: Double,
        sequentialCurves: (CubicCurve.() -> Unit)? = null
    ) {
        data += "C $control1X $control1Y, $control2X $control2Y, $destX $destY "
        if (sequentialCurves != null) data += CubicCurve().apply(sequentialCurves).toString()
    }

    fun cubicCurve(
        control1DX: Double,
        control1DY: Double,
        control2DX: Double,
        control2DY: Double,
        destDX: Double,
        destDY: Double,
        sequentialCurves: (CubicCurve.() -> Unit)? = null
    ) {
        data += "c $control1DX $control1DY, $control2DX $control2DY, $destDX $destDY "
        if (sequentialCurves != null) data += CubicCurve().apply(sequentialCurves).toString()
    }

    fun quadraticCurveTo(
        controlX: Double,
        controlY: Double,
        destX: Double,
        destY: Double,
        sequentialCurves: (QuadraticCurve.() -> Unit)? = null
    ) {
        data += "Q $controlX $controlY, $destX $destY "
        if (sequentialCurves != null) data += QuadraticCurve().apply(sequentialCurves).toString()
    }

    fun quadraticCurve(
        controlDX: Double,
        controlDY: Double,
        destDX: Double,
        destDY: Double,
        sequentialCurves: (QuadraticCurve.() -> Unit)? = null
    ) {
        data += "q $controlDX $controlDY, $destDX $destDY "
        if (sequentialCurves != null) data += QuadraticCurve().apply(sequentialCurves).toString()
    }

    override fun toString(): String = flatTag("path") {
        appendProperty(::d)

        appendCommon()
    }

    class CubicCurve {
        private val data = StringBuilder()
        fun sequential(
            control2DX: Double,
            control2DY: Double,
            destDX: Double,
            destDY: Double
        ) {
            data += "s $control2DX $control2DY, $destDX $destDY "
        }

        fun sequentialTo(
            control2X: Double,
            control2Y: Double,
            destX: Double,
            destY: Double
        ) {
            data += "S $control2X $control2Y, $destX $destY "
        }

        override fun toString(): String = data.toString()
    }

    class QuadraticCurve {
        private val data = StringBuilder()
        fun sequential(
            destDX: Double,
            destDY: Double
        ) {
            data += "t $destDX $destDY "
        }

        fun sequentialTo(
            destX: Double,
            destY: Double
        ) {
            data += "T $destX $destY "
        }

        override fun toString(): String = data.toString()
    }
}