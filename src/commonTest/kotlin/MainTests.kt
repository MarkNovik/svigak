package me.mark.svigak

import kotlinx.io.files.Path
import kotlinx.io.files.sink
import kotlinx.io.writeString
import me.mark.svigak.W3CColor.fuchsia
import me.mark.svigak.W3CColor.maroon
import kotlin.test.Test
import kotlin.test.assertTrue

class MainTests {
    @Test
    fun test() {
        val svg = svg(320.px, 240.px) {
            rect {
                width = 100.pct
                height = 100.pct
                fill = maroon
            }
            rect {
                width = 40.pct
                height = 60.pct
                fill = fuchsia
            }
        }
        val out = Path("test.svg").sink()
        out.writeString(svg.toString())
        out.flush()
        assertTrue(true)
    }
}