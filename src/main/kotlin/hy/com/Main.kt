package hy.com

fun main(args: Array<String>) {
    val child = Child("zhang san", 12)
    println(Child.haha())

    child.firstProperty = "this is new first property"
    println(child)
    child.function()

//    foo()
}

/**
 * 通过@go_on的形式，可以跳过循环当前的执行逻辑，继续执行下一逻辑
 */
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

