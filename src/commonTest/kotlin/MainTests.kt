package me.mark.svigak

import kotlinx.io.files.Path
import kotlinx.io.files.sink
import kotlinx.io.writeString
import me.mark.svigak.W3CColor.*
import kotlin.test.Test
import kotlin.test.assertTrue

class MainTests {
    @Test
    fun test() {
        val svg = svg(320.px, 240.px) {
            rect {
                width = 100.pct
                height = 100.pct
                fill = white
            }
            circle {
                cx = 50.pct
                cy = 50.pct
                r = 100.px
                fill = red
            }
            elem("circle") {
                var cx by attribute(0.px)
                var cy by attribute(0.px)
                var r by attribute(0.px)

                cx = 30.pct
                cy = 30.pct
                r = 10.pct
                fill = yellow
            }
        }
        val out = Path("test.svg").sink()
        out.writeString(svg.toString())
        out.flush()
        assertTrue(true)
    }
}