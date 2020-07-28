package hy.com

fun main(args: Array<String>) {
    val child = Child("zhang san", 12)
    println(Child.haha())

//    foo()
}

fun foo() {
    listOf(1, 2, 3, 4, 5).forEach go_on@{
        if (it == 3)
            return@go_on
        print("$it ")
    }

    println()

    val array = listOf("11", "22", "33", "44", "55")
    for (x in array) {
        print("$x ")
    }
}

