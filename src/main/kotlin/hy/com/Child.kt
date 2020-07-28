package hy.com

/**
 * Created by huangyong on 2020/7/22
 * description :
 */
class Child(name: String, age: Int) : Father(name, age) {
    /**
     * kotlin中的Getter和setter定义好后，外部类不需要手动去调用，直接通过属性为其设值即可。
     * child.firstProperty = "this is new first property"
     * println(child)
     */
    var firstProperty = "the first property is $name".also(::println)
        get() = field.toUpperCase()
        set(value) {
            field = value
        }

    /**
     * companion object里面的方法或者成员可以直接通过类名来访问，相当于java中的public static成员
     */
    companion object {
        fun haha(): String = "this is child fun"
    }

    /**
     * 成员函数与扩展函数
     */
    fun function() {
        println("this is child class method")
    }

    override fun xixi(): String = "this is child fun xixi"

    inner class InnerChild {
        fun haha1() {
            Child.haha()
        }
    }

    override fun toString(): String {
        return "my firstProperty is $firstProperty"
    }
}

fun Child.function(i: Int) {
    println("this is child class extension method")
}
