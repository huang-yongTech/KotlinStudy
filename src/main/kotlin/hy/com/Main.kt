package hy.com

fun main(args: Array<String>) {
//    val child = Child("zhang san", 12)
//    println(Child.haha())
//
//    child.firstProperty = "this is new first property"
//    println(child)
//    child.function()


    val b = BaseImpl(10)
    val derived = Derived(b)
    derived.print()
    println(derived.message)
}

/**
 * 在委托模式中，如果子类重写了父类的相关属性，但没有重写跟属性相关的方法，
 * 那么子类在调用继承的方法，在方法内部访问的依然是父类的属性
 */
interface Base {
    val message: String
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override val message = "BaseImpl: x = $x"
    override fun print() {
        println(message)
    }
}

class Derived(b: Base) : Base by b {
    // 在 b 的 `print` 实现中不会访问到这个属性
    override val message = "Message of Derived"
}

/**
 * 枚举使用
 */
enum class RGB { RED, GREEN, BLUE }

inline fun <reified T : Enum<T>> printAllValues() {
    print(enumValues<T>().joinToString { it.name })
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

