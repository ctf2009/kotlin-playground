package com.ctf.example.coroutines


import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

@Suppress("MagicNumber")
suspend fun main() {

    val timeMillis = measureTimeMillis {
        coroutineScope {
            repeat(10) {
                launch {
                    println("Starting coroutine $it")
                    delay(5000)
                    println("Finishing coroutine $it")
                }
            }

            println("Is Active?: ${coroutineContext[Job]?.isActive}")
            println("Number Children ${coroutineContext[Job]?.children?.count()}")

            // Cancel the above created Coroutines (They will not complete)
            // The Job then run in is still active so others can be launched
            coroutineContext.cancelChildren()

            launch {
                delay(1000)
                println("This is another coroutine which should run before the others finish")
            }

            // Wait for the previous coroutines to join
            coroutineContext[Job]?.children?.forEach { it.join() }

            val job = Job()
            launch(job) {
                println("This should run after waiting for the other coroutines to complete")
            }

            launch(job) {
                delay(4000)
                println("Final Coroutine in scope which will NEVER be called as is cancelled")
            }

            delay(2000)
            job.cancelAndJoin()
        }
    }

    println("Finishing in $timeMillis millis")
}
