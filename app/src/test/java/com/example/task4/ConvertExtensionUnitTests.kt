package com.example.task4

import org.junit.Assert.*
import org.junit.Test


class ConvertExtensionUnitTests {
    @Test
    fun convert_validCurrencies_returnsConvertedValue() {
        val holder = RateHolder(
            mutableMapOf(
                "USD" to 40.0,
                "EUR" to 44.0
            )
        )

        val result = holder.convert(100.0, "USD", "EUR")
        val expected = 100.0 * (40.0 / 44.0)

        assertEquals(expected, result)
    }

    @Test
    fun convert_missingFromCurrency_returnsNull() {
        val holder = RateHolder(
            mutableMapOf("EUR" to 44.0)
        )

        val result = holder.convert(100.0, "USD", "EUR")
        assertNull(result)
    }

    @Test
    fun convert_missingToCurrency_returnsNull() {
        val holder = RateHolder(
            mutableMapOf("USD" to 40.0)
        )

        val result = holder.convert(100.0, "USD", "EUR")
        assertNull(result)
    }

    @Test
    fun convert_negativeAmount_convertsCorrectly() {
        val holder = RateHolder(
            mutableMapOf("USD" to 40.0, "EUR" to 44.0)
        )

        val result = holder.convert(-50.0, "USD", "EUR")
        val expected = -50.0 * (40.0 / 44.0)

        assertEquals(expected, result)
    }
}