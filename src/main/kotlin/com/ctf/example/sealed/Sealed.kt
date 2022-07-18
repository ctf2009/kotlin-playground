package com.ctf.example.sealed

sealed class Fruit {
    abstract fun getFruitName(): String

    // Nested inside the Sealed Class
    class Apple(private val adjective: String) : Fruit() {
        override fun getFruitName(): String {
            return "I am a $adjective Apple"
        }
    }
}

// In the same file as the Sealed Class
class Orange(private val adjective: String) : Fruit() {
    override fun getFruitName(): String {
        return "I am a $adjective Orange"
    }
}
