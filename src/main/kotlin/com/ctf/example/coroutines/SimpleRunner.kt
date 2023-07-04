package com.ctf.example.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("MagicNumber")
suspend fun main() {

    coroutineScope {
        launch {
            delay(3000)
            println("First to Run")
        }

        launch {
            delay(1500)
            println("Second to Run")
        }
    }

    // CoroutineScope will wait for all its coroutines to run
    println("Last to Run")
}
