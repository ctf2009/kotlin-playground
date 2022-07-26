package com.ctf.example.dsl2

import com.fasterxml.jackson.databind.ObjectMapper

fun runDslEmployeeListExample() {

    val employeeList = employeeListBuilder {
        employee {
            name { "Mr A" }
            email { "a@example.com" }
            phoneNumber {
                number("1234567")
            }
        }

        employee {
            name { "Mr B" }
            withEmail("b@example.com")
            phoneNumber {
                number("7654321")
                +"9879652"
                +"1223445"
                number("667443")
            }
        }
    }

    println(
        ObjectMapper()
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(employeeList.build())
    )
}
