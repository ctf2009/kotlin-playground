package com.ctf.example.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@Suppress("MagicNumber")
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main() {

    coroutineScope {

        var count1 = 0
        repeat(10_000) {
            launch {
                delay(500)
                count1++
            }
        }

        println("Coroutine Count: ${coroutineContext.job.children.count()}")
        coroutineContext.job.children.forEach { it.join() }

        // This will NOT equal 10_000 due to race conditions
        println(count1)

        var count2 = 0
        val dispatcher = Dispatchers.IO.limitedParallelism(1)
        repeat(10_000) {
            launch(dispatcher) {
                delay(500)
                count2++
            }
        }

        println("Coroutine Count: ${coroutineContext.job.children.count()}")
        coroutineContext.job.children.forEach { it.join() }

        // This WILL be 10_000 due to the limited parallelism
        println(count2)
    }
}
