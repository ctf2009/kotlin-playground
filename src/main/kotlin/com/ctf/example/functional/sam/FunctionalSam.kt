package com.ctf.example.functional.sam

import com.ctf.example.functional.sam.SamTester.DifferentConsumer
import java.util.function.Consumer

@Suppress("MaxLineLength")
fun runFunctionalSamExample() {

    // Instance of a Java Object
    // The single method on it accepts a Java Functional Interface
    val samTester = SamTester()

    // Ordinarily SAM conversion just works
    samTester.doSomething { println("Printing from Kotlin Lambda as SAM conversion: \'$it\'") }

    //  When there is ambiguity, you need to be explicit( Consumer { } or DifferentConsumer { }
    //          Overload resolution ambiguity. All these functions match.
    //          public open fun doSomething(consumer: SamTester.DifferentConsumer<String!>!): Unit defined in com.ctf.example.functional.sam.SamTester
    //          public open fun doSomething(consumer: Consumer<String!>!): Unit defined in com.ctf.example.functional.sam.SamTester
    samTester.doSomethingAmbiguous(Consumer { println("Printing from Kotlin Lambda as SAM conversion: \'$it\'") })
    samTester.doSomethingAmbiguous(DifferentConsumer { println("Printing from Kotlin Lambda as SAM conversion: \'$it\'") })

    // Using the Kotlin Migrated version of SamTester
    val kotlinSamTester = KotlinSamTester()

    // Looks like with the latter versions of Kotlin this just works
    kotlinSamTester.doSomething { println("Printing from Kotlin Lambda as SAM conversion: \'$it\'") }

    // Same as calling Java
    kotlinSamTester.doSomethingAmbiguous(Consumer { println("Printing from Kotlin Lambda as SAM conversion: \'$it\'") })
    kotlinSamTester.doSomethingAmbiguous(DifferentConsumer { println("Printing from Kotlin Lambda as SAM conversion: \'$it\'") })

    // With KotlinSamTester and Kotlin Interfaces

    // Type mismatch.
    // Required:
    //  KotlinConsumer<String>
    // Found:
    //  () → Unit
    // kotlinSamTester.doSomethingKotlin { println("Printing from Kotlin Lambda as SAM conversion: \'$it\'") }

    // Interface KotlinConsumer does not have constructors
    // kotlinSamTester.doSomethingAmbiguousKotlin(KotlinConsumer { println("Printing from Kotlin Lambda as SAM conversion: \'$it\'") })

    // Interface DifferentKotlinConsumer does not have constructors
    // kotlinSamTester.doSomethingAmbiguousKotlin(DifferentKotlinConsumer { println("Printing from Kotlin Lambda as SAM conversion: \'$it\'") })

    // You need to use Kotlin Functional Interfaces to make the above work
    kotlinSamTester.doSomethingKotlinFunctional { println("Printing from Kotlin Lambda as SAM conversion with Functional Interface: \'$it\'") }
    kotlinSamTester.doSomethingAmbiguousKotlinFunctional(FunctionalKotlinConsumer { println("Printing from Kotlin Lambda as SAM conversion with Functional Interface: \'$it\'") })
    kotlinSamTester.doSomethingAmbiguousKotlinFunctional(FunctionalDifferentKotlinConsumer { println("Printing from Kotlin Lambda as SAM conversion with Functional Interface: \'$it\'") })
}

// Migrate the Java Class to Kotlin
// Calling from Java works the same from Java
class KotlinSamTester {

    fun doSomething(consumer: Consumer<String>) {
        println("Doing Something with Consumer with Consumer Arg")
        consumer.accept(SamTester.VALUE)
    }

    fun doSomethingAmbiguous(consumer: Consumer<String?>) {
        println("Doing Something with Consumer with Consumer Arg")
        consumer.accept(SamTester.VALUE)
    }

    fun doSomethingAmbiguous(consumer: DifferentConsumer<String?>) {
        println("Doing Something with Consumer with DifferentConsumer Arg")
        consumer.accept(SamTester.VALUE)
    }

    // Migrating the above to use a Kotlin Interface
    fun doSomethingKotlin(consumer: KotlinConsumer<String>) {
        println("Doing Something with Consumer with Consumer Arg")
        consumer.accept(SamTester.VALUE)
    }

    fun doSomethingAmbiguousKotlin(consumer: KotlinConsumer<String?>) {
        println("Doing Something with Consumer with Consumer Arg")
        consumer.accept(SamTester.VALUE)
    }

    fun doSomethingAmbiguousKotlin(consumer: DifferentKotlinConsumer<String?>) {
        println("Doing Something with Consumer with DifferentKotlinConsumer Arg")
        consumer.accept(SamTester.VALUE)
    }

    // Using Kotlin Functional Interfaces
    fun doSomethingKotlinFunctional(consumer: FunctionalKotlinConsumer<String>) {
        println("Doing Something with Consumer with Consumer Arg")
        consumer.accept(SamTester.VALUE)
    }

    fun doSomethingAmbiguousKotlinFunctional(consumer: FunctionalKotlinConsumer<String>) {
        println("Doing Something with Consumer with Consumer Arg")
        consumer.accept(SamTester.VALUE)
    }

    fun doSomethingAmbiguousKotlinFunctional(consumer: FunctionalDifferentKotlinConsumer<String>) {
        println("Doing Something with Consumer with DifferentKotlinConsumer Arg")
        consumer.accept(SamTester.VALUE)
    }

}

// Standard Interfaces
interface KotlinConsumer<T> {
    fun accept(value: T)
}

interface DifferentKotlinConsumer<T> {
    fun accept(value: T)
}

// Functional Interfaces
fun interface FunctionalKotlinConsumer<T> {
    fun accept(value: T)
}

fun interface FunctionalDifferentKotlinConsumer<T> {
    fun accept(value: T)
}


