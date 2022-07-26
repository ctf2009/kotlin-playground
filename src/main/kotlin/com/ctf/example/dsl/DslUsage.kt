package com.ctf.example.dsl

fun htmlBuilder(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}

fun runDslExample() {
    val html = htmlBuilder {
        head {
            title {
                attributes["test"] = "value"
                text("This is a Title in a HTML Document")
            }
        }
        body {
            h1 { text("This is a H1 tag") }

            b { text("This is Bold") }

            p { text("This is a paragraph ") }

            a("https://example.com") {
                text("This is an Anchor")
            }
        }
    }

    val builder = StringBuilder()
    html.render("", builder)
    println(builder)
}

fun main() {
    runDslExample()
}
