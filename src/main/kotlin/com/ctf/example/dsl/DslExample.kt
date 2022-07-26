package com.ctf.example.dsl

const val INDENT = "    "

@DslMarker
annotation class HtmlTagMarker

interface Element {
    fun render(indent: String, builder: StringBuilder)
}

class TextElement(private val text: String) : Element {
    override fun render(indent: String, builder: StringBuilder) {
        builder.append("$indent$text\n")
    }
}

@HtmlTagMarker
abstract class Tag(private val tagName: String) : Element {
    val children = arrayListOf<Element>()
    val attributes = hashMapOf<String, String>()

    fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    override fun render(indent: String, builder: StringBuilder) {
        builder.append("$indent<$tagName${renderAttributes()}>\n")
        children.forEach { it.render(indent + INDENT, builder) }
        builder.append("$indent</$tagName>\n")
    }

    private fun renderAttributes(): String {
        val builder = StringBuilder()
        for ((attr, value) in attributes) {
            builder.append(" $attr=\"$value\"")
        }
        return builder.toString()
    }
}

abstract class TagWithText(name: String) : Tag(name) {
    fun text(text: String) {
        children.add(TextElement(text))
    }
}

class HTML : TagWithText("html") {
    fun head(init: Head.() -> Unit) = initTag(Head(), init)

    fun body(init: Body.() -> Unit) = initTag(Body(), init)
}

class Head : TagWithText("head") {
    fun title(init: Title.() -> Unit) = initTag(Title(), init)
}

class Title : TagWithText("title")

abstract class BodyTag(name: String) : TagWithText(name) {
    fun b(init: B.() -> Unit) = initTag(B(), init)
    fun p(init: P.() -> Unit) = initTag(P(), init)
    fun h1(init: H1.() -> Unit) = initTag(H1(), init)
    fun a(href: String, init: A.() -> Unit) {
        val a = initTag(A(), init)
        a.href = href
    }
}

class Body : BodyTag("body")
class B : BodyTag("b")
class P : BodyTag("p")
class H1 : BodyTag("h1")

class A : BodyTag("a") {
    var href: String
        get() = attributes["href"]!!
        set(value) {
            attributes["href"] = value
        }
}
