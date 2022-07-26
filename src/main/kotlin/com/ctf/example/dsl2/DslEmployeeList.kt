package com.ctf.example.dsl2

@DslMarker
annotation class InterfaceMarker


data class Employee(val name: String, val email: String, val phoneNumbers: List<String>)
data class EmployeeList(val employees: MutableList<Employee> = mutableListOf())

@InterfaceMarker
class PhoneNumberBuilder {
    private val phoneNumbers = arrayListOf<String>()

    operator fun String?.unaryPlus() {
        this?.let { value ->
            phoneNumbers.add(value)
        }
    }

    fun number(number: String): PhoneNumberBuilder {
        phoneNumbers.add(number)
        return this
    }

    fun build(): List<String> {
        return phoneNumbers
    }
}

@InterfaceMarker
class EmployeeBuilder {
    private var name: String = ""
    private var email: String = ""
    private var phoneNumbers = PhoneNumberBuilder()

    fun name(nameProvider: () -> String): EmployeeBuilder {
        name = nameProvider()
        return this
    }

    fun email(emailProvider: () -> String): EmployeeBuilder {
        email = emailProvider()
        return this
    }

    fun phoneNumber(init: PhoneNumberBuilder.() -> Unit): EmployeeBuilder {
        phoneNumbers.init()
        return this
    }

    fun build(): Employee {
        return Employee(name, email, phoneNumbers.build())
    }

    infix fun EmployeeBuilder.withEmail(value: String) {
        this.email { value }
    }
}

class EmployeeListBuilder {
    private var employees = mutableListOf<Employee>()

    fun employee(lambda: EmployeeBuilder.() -> Unit) {
        employees.add(EmployeeBuilder().apply(lambda).build())
    }

    fun build(): EmployeeList {
        return EmployeeList(employees)
    }
}

fun employeeListBuilder(init: EmployeeListBuilder.() -> Unit): EmployeeListBuilder {
    val employeeListBuilder = EmployeeListBuilder()
    employeeListBuilder.init()
    return employeeListBuilder
}
