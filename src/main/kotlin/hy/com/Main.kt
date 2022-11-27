package hy.com

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import java.io.IOException
import kotlin.system.measureTimeMillis

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@FlowPreview
fun main(args: Array<String>): Unit = runBlocking<Unit> {
    val baseImpl = BaseImpl(1)
    val baseImpl1 = BaseImpl1(2)
    println(baseImpl1.message)
}

data class Ball(var hits: Int)

/**
 * 协程通道公平性验证
 */
suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) {
        ball.hits++
        println("$name $ball")
        delay(500)
        table.send(ball)
    }
}

suspend fun CoroutineScope.sendString(channel: SendChannel<String>, s: String, time: Long) {
    while (true) {
        delay(time)
        channel.send(s)
    }
}

/**
 * 协程管道的使用
 */
@ExperimentalCoroutinesApi
fun CoroutineScope.numbersFrom(x: Int): ReceiveChannel<Int> = produce {
    var start = x
    while (true) {
        send(start++)
    }
}

@ExperimentalCoroutinesApi
fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int): ReceiveChannel<Int> = produce {
    for (x in numbers) {
        if (x % prime != 0) {
            send(x)
        }
    }
}

@ExperimentalCoroutinesApi
fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (msg in channel) {
        println("Processor $id received $msg")
    }
}

/**
 * 协程管道的使用
 */
@ExperimentalCoroutinesApi
fun CoroutineScope.produceNumbers(): ReceiveChannel<Int> = produce {
    var x = 1
    while (true) {
        send(x++)
        delay(100)
    }
}

@ExperimentalCoroutinesApi
fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) {
        send(x * x)
    }
}

@ExperimentalCoroutinesApi
fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) {
        delay(1000)
        send(x)
    }
}

fun requestFlow(i: Int) = flow {
    emit("$i : First")
    delay(500)
    emit("$i : Second")
}

fun numbers(): Flow<Int> = flow {
    try {
        emit(1)
        emit(2)
        println("this line will not be executed")
        emit(3)
        emit(4)
    } finally {
        println("finally in numbers")
    }
}

suspend fun performRequest(request: Int): String {
    delay(1000) // 模仿⻓时间运⾏的异步⼯作
    return "response $request"
}

fun foo(): Flow<Int> = flow {
    println("Flow started")
    for (i in 1..3) {
        println("Emitting $i")
        delay(1000L)
        emit(i)
    }
}

/**
 * 日志打印
 */
fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

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

/**
 * 协程流式处理
 */
//foo()
//.onEach { value ->
//    check(value = value <= 1) { "Collect value $value" }
//    println(value)
//}
//.onCompletion { cause ->
//    if (cause != null) {
//        println("Flow completed exceptionally")
//    } else {
//        println("Done")
//    }
//}
//.catch { e -> println("Caught $e") }
//.launchIn(this)
//
//println("Main coroutine is done")

//val threadLocal = ThreadLocal<String>()
//threadLocal.set("main")
//println("Pre-main, current thread: ${Thread.currentThread()}, thread local value:'${threadLocal.get()}'")
//val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
//    println("Launch start, current thread: ${Thread.currentThread()}, thread local value:'${threadLocal.get()}'")
//    yield()
//    println("After yield, current thread: ${Thread.currentThread()}, thread local value:'${threadLocal.get()}'")
//}
//job.join()
//println("Post-main, current thread: ${Thread.currentThread()}, thread local value:'${threadLocal.get()}'")

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
    fun print() {
        println("这是base的默认实现")
    }
}

open class BaseImpl(x: Int) : Base {
    override var message = "BaseImpl: x = $x"
    override fun print() {
        println(message)
    }
}

class BaseImpl1(x: Int) : BaseImpl(x) {
    override var message = "BaseImpl1: x = $x"

    override fun print() {
        println(message)
    }
}

val BaseImpl1.message: String
    get() = "扩展属性：2"

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
fun func() {
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

