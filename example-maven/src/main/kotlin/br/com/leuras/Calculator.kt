package br.com.leuras

object Calculator {

    fun add(a: Int, b: Int) = a + b

    fun subtract(a: Int, b: Int) = a - b

    fun multiply(a: Int, b: Int) = a * b

    fun divide(a: Int, b: Int) = b.takeUnless { it == 0 }
        ?.let { a / b }
        ?: throw IllegalArgumentException("'b' must be greater than or equal to 1")

}