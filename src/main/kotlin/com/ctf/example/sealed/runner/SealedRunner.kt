package com.ctf.example.sealed.runner

import com.ctf.example.sealed.Fruit
import com.ctf.example.sealed.Orange

// Inheritor of sealed class or interface declared in package com.ctf.example.sealed.runner,
// but it must be in package com.ctf.example.sealed where base class is declared

// Needs to be declared in the same package as the Sealed Class

//     class Banana(private var adjective: String): Fruit {
//         override fun getFruitName(): String {
//             return "I am a $adjective Banana"
//         }
//     }


// Non Sealed Abstract Class
@Suppress("UnnecessaryAbstractClass")
abstract class AnotherFruit {
    abstract fun getFruitName(): String
}

class Pear(private var adjective: String) : AnotherFruit() {
    override fun getFruitName(): String {
        return "I am a $adjective Pair"
    }
}

class Strawberry(private val adjective: String) : AnotherFruit() {
    override fun getFruitName(): String {
        return "I am a $adjective Strawberry"
    }
}

fun runSealedExample() {
    val sealedTypes = arrayListOf(
        Fruit.Apple("Small"),
        Orange("Shiny")
    )

    // Need to provide all known
    // 'when' expression must be exhaustive, add necessary 'is Orange' branch or 'else' branch instead
    sealedTypes.forEach {
        when (it) {
            is Fruit.Apple -> { println("The Apple says: ${it.getFruitName()}") }
            is Orange -> { println("The Orange says: ${it.getFruitName()}") }
        }
    }

    val unsealedTypes = arrayListOf(
        Pear("Tasty"),
        Strawberry("Rosy Red")
    )

    // You do not have to provide an branch for Strawberry or an else branch on non sealed classes
    unsealedTypes.forEach {
        when (it) {
            is Pear -> { println("The Pear says: ${it.getFruitName()}") }
           // is Strawberry -> { println("The Strawberry says: ${it.getFruitName()}") }
        }
    }
}
