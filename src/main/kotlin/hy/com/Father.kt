package hy.com

/**
 * Created by huangyong on 2020/7/22
 * description :
 */
open class Father(name: String, age: Int) {

    init {
        println("this is father code, name is $name, age is $age")
    }

    val fatherName = "this is father name : $name".also(::println)
    val fatherAge = "this is father age : $age".also(::println)

    open fun xixi() :String = "this is father xixi fun"
}