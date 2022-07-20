package com.ctf.example.infix

data class ExampleClass(var field1: String, var field2: String)

infix fun String.isNotEqualTo(value: String): Boolean {
    return this != value
}

infix fun ExampleClass.mergeField1With(value: String) {
    this.field1 = "$field1: $value"
}

fun ExampleClass.fieldShouldBe(value: String, fieldGetter: ExampleClass.() -> String) {
    if (value isNotEqualTo fieldGetter()) {
        error("Not Equal")
    }
}

fun runInfixExample() {
    val example = ExampleClass("field1", "field2")
    println(example)

    // Calling Infix Function
    example mergeField1With "additional info"

    // Standard Extension Function
    example.fieldShouldBe("field1: additional info") { this.field1 }
}
