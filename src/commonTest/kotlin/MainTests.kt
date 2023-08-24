package me.mark.svigak

import kotlinx.io.files.Path
import kotlinx.io.files.sink
import kotlinx.io.writeString
import me.mark.svigak.W3CColor.*
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalStdlibApi::class)
class MainTests {
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
            text {
                fill = fuchsia
                stroke = yellow
                var y by attributes(50.pct)
                var x by attributes(50.pct)
                var textAnchor by attributes("middle", "text-anchor")
                var size by attributes(3.em, "font-size")

                content = "Hello everynyan!"
            }
        }
        Path("text.svg").sink().use {
            it.writeString(text.toString())
            it.flush()
        }
        assertTrue(true)
    }

    @Test
    fun use() {
        val svg = svg(500.px) {
            rect {
                width = 100.pct
                height = 100.pct
                fill = fuchsia
            }
            val c = circle {
                cy = 50.pct
                cx = 120.px
                r = 100.px
                fill = yellow
            }
            use(c) {
                x = 260.px
            }
        }
        Path("use.svg").sink().use {
            it.writeString(svg.toString())
        }
    }

    @Test
    fun g() {
        svg(250.px) {
            g {
                fill = green
                stroke = blue

                rect {
                    width = 100.px
                    height = 100.pct
                }
                circle {
                    cx = 50.pct
                    cy = 50.pct
                    r = 30.pct
                }
            }
        }.toString().let { svg ->
            Path("g.svg").sink().use { sink ->
                sink.writeString(svg)
            }
        }
    }

    @Test
    fun animate() {
        val svg = svg(250.px, 250.px) {
            rect {
                width = 100.pct
                height = 100.pct
                fill = white
            }
            rect {
                width = 100.pct
                height = 100.pct
                ::rx.animate {
                    values = listOf(0.px, 50.pct, 0.px)
                    dur = 5.s
                    repeatCount = indefinite
                }
            }
            circle {
                cy = 50.pct
                r = 20.pct
                fill = yellow
                ::cx.animate {
                    values = listOf(20.pct, 80.pct, 20.pct)
                    dur = 5.s
                    repeatCount = indefinite
                }
            }
        }
        Path("animation.svg").sink().use {
            it.writeString(svg.toString())
        }
    }
}