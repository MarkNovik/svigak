package me.mark.svigak

import kotlinx.io.files.Path
import kotlinx.io.files.sink
import kotlinx.io.writeString
import me.mark.svigak.W3CColor.*
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
            path {
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

    @Test
    fun text() {
        val text = svg(500.px) {
            rect { // BG
                width = 100.pct
                height = 100.pct
                fill = black
            }
            add(
                child = object : Element() {
                    var text = ""
                    override fun toString(): String = buildString {
                        append("<text ")
                        appendCommon()
                        append(">$text</text>")
                    }
                }
            ) {
                text = "Hello everynyan!"
                fill = fuchsia
                stroke = yellow
                var y by attributes(50.pct)
                var x by attributes(50.pct)
                var textAnchor by attributes("middle", "text-anchor")
                var size by attributes("3em", "font-size")
            }
        }
        Path("text.svg").sink().use {
            it.writeString(text.toString())
            it.flush()
        }
        assertTrue(true)
    }
}