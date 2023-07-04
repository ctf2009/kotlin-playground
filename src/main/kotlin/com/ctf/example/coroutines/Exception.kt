package com.ctf.example.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@Suppress("MagicNumber")
suspend fun main() {

    val handler = CoroutineExceptionHandler { _, throwable ->
        println("Caught Exception $throwable")
    }

    coroutineScope {
        val job = Job() + handler // If using this, then the other Coroutines WILL cancel
        //val job = SupervisorJob() + handler // If using this, then the other Coroutines will NOT cancel

        repeat(5) { routine ->
            launch(job) {
                println("Starting polling loop $routine")
                repeat(10) { count ->
                    println("Polling Routine $routine is Polling")
                    delay(500)

                    if (routine == 0 && count == 4) {
                        println("Throwing Error for routine $routine")
                        error("Error Thrown for Polling in routine $routine")
                    }
                }
                println("Completed polling loop for $routine")
            }
        }

        job.job.children.forEach { it.join() }
        println("Finished Polling Tasks")

        launch(job) {
            delay(5000)
            println("Last Coroutine to run in the scope - Will Not Run")
        }

    }

    println("Finished Everything")
}


