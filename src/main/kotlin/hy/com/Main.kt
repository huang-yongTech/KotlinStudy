package hy.com

import kotlinx.coroutines.*

fun main(args: Array<String>): Unit = runBlocking<Unit> {
    launch(Dispatchers.Unconfined) { // ⾮受限的——将和主线程⼀起⼯作
        println("Unconfined : I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("Unconfined : After delay in thread ${Thread.currentThread().name}")
    }
    launch { // ⽗协程的上下⽂，主 runBlocking 协程
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
    }
//    launch(Dispatchers.Default) { // 将会获取默认调度器
//        println("Default : I'm working in thread ${Thread.currentThread().name}")
//    }
//    launch(newSingleThreadContext("MyOwnThread")) { // 将使它获得⼀个新的线程
//        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
//    }
}

suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE) // 模拟⼀个⻓时间的运算
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}

suspend fun concurrentSum(): Int = coroutineScope {
    val num1 = somethingUsefulOneAsync()
    val num2 = somethingUsefulTwoAsync()

    num1.await() + num2.await()
}

fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L)
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L)
    return 29
}

//    val child = Child("zhang san", 12)
//    println(Childhaha())
//
//    child.firstProperty = "this is new first property"
//    println(child)
//    child.function()


//    val b = BaseImpl(10)
//    val derived = Derived(b)
//    derived.print()
//    println(derived.message)

/**
 * 函数类型实例调用
 */
//    val stringPlus: (String, String) -> String = String::plus
//    val intPlus: Int.(Int) -> Int = Int::plus
//    println(stringPlus.invoke("<-", "->"))
//    println(stringPlus("Hello, ", "world!"))
//    println(intPlus.invoke(1, 1))
//    println(intPlus(1, 2))
//    println(2.intPlus(3)) // 类扩展调⽤

//    val numbers = listOf("one", "two", "three", "four")
//    val res : Pair<List<String>,List<String>> = numbers.partition { it.length > 3 }
//    println(res.first)
//    println(res.second)


/**
 * 集合过滤及差集计算
 */
//    val words = "A long time ago in a galaxy far far away".split(" ")
//    val shortWords = mutableListOf<String>()
//    words.getShortWordsTo(shortWords, 3)
//    println(shortWords)

fun List<String>.getShortWordsTo(shortWords: MutableList<String>, maxLength: Int) {
    this.filterTo(shortWords) { it.length <= maxLength }
    // throwing away the articles
    val articles = setOf("a", "A", "an", "An", "the", "The")
    //移除shortWords中在articles中出现过的元素，就是取差集
    shortWords -= articles
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

