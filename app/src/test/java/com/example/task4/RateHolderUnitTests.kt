package com.example.task4

import org.junit.Assert.*
import org.junit.Test

class RateHolderUnitTests {

    @Test
    fun getRate_existingCurrency_returnsCorrectValue() {
        val holder = RateHolder(mutableMapOf("USD" to 40.0))
        val rate = holder.getRate("USD")
        assertEquals(40.0, rate)
    }

    @Test
    fun getRate_missingCurrency_returnsNull() {
        val holder = RateHolder(mutableMapOf("USD" to 40.0))
        val rate = holder.getRate("EUR")
        assertNull(rate)
    }

    @Test
    fun setRate_addsNewCurrency() {
        val holder = RateHolder(mutableMapOf<String, Double>())
        holder.setRate("EUR", 42.5)
        assertEquals(42.5, holder.getRate("EUR"))
    }

    @Test
    fun hasCurrency_trueForExisting() {
        val holder = RateHolder(mutableMapOf("USD" to 40.0))
        assertTrue(holder.hasCurrency("USD"))
    }

    @Test
    fun hasCurrency_falseForMissing() {
        val holder = RateHolder(mutableMapOf("USD" to 40.0))
        assertFalse(holder.hasCurrency("EUR"))
    }
}