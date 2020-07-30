package hy.com

/**
 * 实体类的一种写法
 */
data class Person(var name: String, var age: Int)


/**
 * 集合相等性判断
 */
//    val bob = Person("Bob", 31)
//    val people = listOf<Person>(Person("Adam", 20), bob, bob)
//    val people2 = listOf<Person>(Person("Adam", 20), Person("Bob", 31), bob)
//    println(people == people2)
//    bob.age = 32
//    println(people == people2)