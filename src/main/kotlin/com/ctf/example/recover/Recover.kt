package com.ctf.example.recover

import java.time.Instant

const val PARSABLE_STRING = "2022-07-18T04:48:48Z"

fun runRecoverExample() {
    val validSupplier: () -> String = { PARSABLE_STRING }
    val invalidSupplier = { "INVALID" }

    println("runCatchRecover - With Valid Value")
    val result1 = runCatchRecover(validSupplier)
    println(result1)

    println("\nrunCatchRecover - With Invalid Value")
    val result2 = runCatchRecover(invalidSupplier)
    println(result2)

    println("\nrunCatchResult - With Valid Value")
    runCatchResult(validSupplier)

    println("\nrunCatchResult - With Invalid Value")
    runCatchResult(invalidSupplier)
}

fun runCatchRecover(supplier: () -> String): Instant {
    val result = runCatching {
        Instant.parse(supplier.invoke())
    }.recover {
        println("There was an error parsing the String: ${it.message}, Returning Instant.now()")
        Instant.now()
    }

    // This will be false if recover is successful
    println("Result isFailure = ${result.isFailure}")
    return result.getOrThrow()
}

fun runCatchResult(supplier: () -> String) {
    val result = runCatching {
        Instant.parse(supplier.invoke())
    }.onSuccess {
        println("Successful Conversion")
        // Do other stuff on Success

    }.onFailure {
        println("Failed Conversion: ${it.message}")
        // Do Other stuff on Failure

    }.fold(
        // Folding allows us to change the return type.
        // In this instant, the 'result' var could be an Instant or a String
        onSuccess = { it },
        onFailure = { "Hello World" }
    )

    // This could be either an Instant or a String!
    println(result)
}
