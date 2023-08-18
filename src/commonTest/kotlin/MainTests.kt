package me.mark.svigak

import kotlinx.io.files.Path
import kotlinx.io.files.sink
import kotlinx.io.writeString
import me.mark.svigak.W3CColor.aqua
import me.mark.svigak.W3CColor.black
import kotlin.test.Test
import kotlin.test.assertTrue

class MainTests {
    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun test() {
        val svg = svg(190.px, 160.px) {
            rect {
                width = 100.pct
                height = 100.pct
                fill = aqua
            }
            add(Path()) {
                move(10, 80)
                cubicCurveTo(
                    control1X = 40, 10,
                    control2X = 65, 10,
                    destX = 95, 80
                ) {
                    sequentialTo(150, 150, 180, 80)
                }
                close()

                stroke = black
                fill = Custom("transparent")
            }
        }
        Path("test.svg").sink().use {
            it.writeString(svg.toString())
            it.flush()
        }
        assertTrue(true)
    }
}