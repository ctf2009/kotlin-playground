package com.ctf.example.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

// The purpose of this example is to show both how Channels work but more importantly how Errors are dealt with
@Suppress("MagicNumber")
suspend fun main() {

    val handler = CoroutineExceptionHandler { _, throwable ->
        println("Caught Exception $throwable")
    }

    val count = AtomicInteger()
    val time = measureTimeMillis {
        coroutineScope {
            val channel = Channel<String>(capacity = 2000)

            // Using a Job here means if one of the children throws an error then this and the other children
            // will be cancelled
            val job = Job() + handler

            repeat(times = 5) {
                launch(job) {
                    delay(10_000)
                    for (value in channel) {
                        println("Value received: $value in routine $it")
                        count.incrementAndGet()

                        // Test what happens in an error scenario
                        if (count.get() > 4000 && it == 0) {
                            println("Throwing Error for routine 0")
                            error("An error occurred in the polling process")
                        }
                    }
                    println("Routine has finished polling: $it")
                }
            }

            launch(job) {
                try {
                    repeat(10_000) {
                        channel.send("Hello $it")
                        if (it % 100 == 0 && it > 0) {
                            println("Sent $it")
                        }
                    }
                    println("Finished Sending")

                } finally {
                    channel.close()
                    println("Channel closed")
                    println("Was Job Cancelled: ${job.job.isCancelled}")
                }
            }

            // Wait for the Jobs Children to complete
            job.job.children.forEach { it.join() }

            launch {
                println("This should run as it is not part of the Job that was cancelled due to the Error")
            }

            launch(job) {
                println("This will NOT run as the Job was cancelled")
            }
        }
    }

    // This should be displayed but will not be 10_000 as an error would have been thrown
    // causing the producer to cancel
    println("Finished with a total count of $count in $time millis")
}
