@file:Suppress("MagicNumber")

package com.ctf.example.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// This is demonstrating how suspended functions should NOT leave running processes running when it returns
suspend fun main() {
    println("Starting")

    coroutineScope {
        println("Top Level CoroutineContext $this.coroutineContext")
        doStuff() // This will pause here whilst the doStuff completes as is in a new CoroutineScope
        doOtherStuff()
        println("Finished All Actions")
    }

    println("Finishing")
}

suspend fun doStuff() = coroutineScope {
    launch {
        println("Doing Stuff with context ${this.coroutineContext}")
        delay(8_000)
        println("Finished Stuff")
    }

    // https://maxkim.eu/things-every-kotlin-developer-should-know-about-coroutines-part-2-coroutinescope#heading-coroutine-launchers
    // Nothing should be left running in the background when a suspending function returns
    CoroutineScope(Dispatchers.IO).launch {
        println("Doing extra stuff. This will continue despite the doStuff() function returning")
        delay(15_000)
        println("Finished doing extra stuff")
    }
}

fun CoroutineScope.doOtherStuff() =
    launch {
        println("Doing other Stuff with context ${this.coroutineContext}")
        delay(12_000)
        println("Finished Other Stuff")
    }

