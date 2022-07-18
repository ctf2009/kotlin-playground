package com.ctf.example.receiver

fun <T> applyWithoutReceiver(value: T, actions: (T) -> Unit): T {
    actions.invoke(value)
    return value
}

fun <T> applyWithReceiverNonExtension(value: T, actions: T.() -> Unit): T {
    actions(value)
    return value
}

fun <T> T.applyWithReceiver(actions: T.() -> Unit): T {
    actions()
    return this
}

fun runReceiverExample() {

    // Without Receiver
    val builder = StringBuilder()
    applyWithoutReceiver(builder) {
        it.append("Hello")
        it.append("\n")
        it.append("World")
    }

    println(builder)

    // With Receiver and Extension
    val builder2 = StringBuilder()
    applyWithReceiverNonExtension(builder2) {
        append("Hello")
        append("\n")
        append("World")

    }

    println(builder2)

    // With Receiver and Extension
    val builder3 = StringBuilder()
        .applyWithReceiver {
            append("Hello")
            append("\n")
            append("World")
        }

    println(builder3)
}
