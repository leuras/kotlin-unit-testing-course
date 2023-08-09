package br.com.leuras

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CalculatorTest {

    @Test
    fun `should successfully sum two numbers`() {
        val expected = 12
        val actual = Calculator.add(7, 5)

        assertEquals(expected, actual)
    }

    @Test
    fun `should successfully subtract two numbers`() {
        val expected = -5
        val actual = Calculator.subtract(5, 10)

        assertEquals(expected, actual)
    }

    @Test
    fun `should successfully multiply two numbers`() {
        val expected = 49
        val actual = Calculator.multiply(7, 7)

        assertEquals(expected, actual)
    }

    @Test
    fun `should successfully divide two numbers`() {
        val expected = 8
        val actual = Calculator.divide(32, 4)

        assertEquals(expected, actual)
    }

    @Test
    fun `should throw exception when call invoking a division and b is 0`() {
        assertThrows<IllegalArgumentException> { Calculator.divide(1, 0)}
    }
}