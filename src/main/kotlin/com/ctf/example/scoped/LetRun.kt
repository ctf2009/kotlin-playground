@file:Suppress("MatchingDeclarationName")
package com.ctf.example.scoped

data class ExampleClass(val example: String)

@Suppress("ComplexMethod")
fun main() {

    // 'let' is typically used for nullable objects to avoid NullPointerException
    val potentiallyNullableInstance: ExampleClass? = null

    // If we wanted to check an instance is not null before printing a field that exists in the instance in regular Java
    if (potentiallyNullableInstance != null) {
        val result = potentiallyNullableInstance.example
        println(result)
    }

    // In Kotlin, we can use the 'let' scoped function to make this a bit easier to read. This does the same as above
    potentiallyNullableInstance?.let {
        println(it.example)
    }

    // 'let' accepts a lambda which accepts the instance (ExampleClass in this case) and allows you to perform actions
    // when using 'let', by default the scoped instance used 'it'. You can change this
    // The below is the same as above
    potentiallyNullableInstance?.let { exampleClass ->
        println(exampleClass.example)
    }

    // Using 'it' or any other name for the scoped variable can be a bit annoying. Instead of 'let' we could use 'run'
    // We refer to the scoped variable as 'this'. Using run can feel like a better approach to using 'let'
    // Overall use what you feel most comfortable with
    potentiallyNullableInstance?.run {
        println(this.example)
    }

    // ------- Other notes about 'let' and 'run' -------

    // In a Java scenario we usually check an object is not null before trying to grab a variable from it
    var maybeNullValue: String? = null
    if (potentiallyNullableInstance != null) {
        maybeNullValue = potentiallyNullableInstance.example
    }

    // With Kotlin, if statements are expressions
    maybeNullValue = if (potentiallyNullableInstance != null) potentiallyNullableInstance.example else null

    // We can use 'let' instead
    // This means you don't have to create the variable you want to assign to first
    maybeNullValue = potentiallyNullableInstance?.let { it.example }

    // We can use 'run' also
    maybeNullValue = potentiallyNullableInstance?.run { this.example }

    // You can also use 'let' and 'run' with the elvis operator
    maybeNullValue = potentiallyNullableInstance?.let { it.example } ?: "UNKNOWN"
    maybeNullValue = potentiallyNullableInstance?.run { this.example } ?: "UNKNOWN"

    // The elvis operator is NOT a replacement for Java's shorthand if however
    // Here is an example that demonstrates how the elvis operator could be misused
    var example = ExampleClass(example = "This is an Example")
    var result = example?.let {
        it.example
        println("Inside 'let' as expected as example is not null")
        callFunctionThatReturnsNull()
    } ?: run {
        println("Made it to the elvis operator")
        "elvis"
    }

    println(result)
    // The above prints the following
    // Inside 'let' as expected as example is not null
    // Made it to the elvis operator
    // elvis

    // This is because the 'let' function makes a call to another function which has the ability to return null
    // When this is the last statement in the 'let' then this will immediately jump to the elvis side
    // In this instance, the expected result should have been a null return, but it actually is set to 'elvis'
    // With the shorthand Java scenario only one side is ever evaluated but this is not the case with the elvis operator
    // The 'shorthand' Kotlin variant is actually if (something) something else somethingElse
}

@Suppress("FunctionOnlyReturningConstant")
fun callFunctionThatReturnsNull(): String? {
    return null
}
