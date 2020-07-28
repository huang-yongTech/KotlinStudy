package hy.com

/**
 * Created by huangyong on 2020/7/22
 * description :
 */
class Child(name: String, age: Int) : Father(name, age) {
    var firstProperty = "the first property is $name".also(::println)
        get() = field
        set(value) {
        }

    companion object {
        fun haha(): String = "this is child fun"
    }

    override fun xixi(): String = "this is child fun xixi"

    inner class InnerChild {
        fun haha1() {
            Child.haha()
        }
    }
}
