package com.example.task4

import org.junit.Assert.assertEquals
import org.junit.Test

class RateDelegateUnitTests {

    class CurrencyViewModel(val holder: RateHolder<Double>) {
        var usd by RateDelegate(holder, "USD")
        var eur by RateDelegate(holder, "EUR")
    }

    @Test
    fun delegate_getValue_returnsHolderValue() {
        val holder = RateHolder(mutableMapOf("USD" to 40.0))
        val vm = CurrencyViewModel(holder)

        assertEquals(40.0, vm.usd, 0.0001)
    }

    @Test
    fun delegate_setValue_updatesHolderValue() {
        val holder = RateHolder(mutableMapOf("USD" to 40.0))
        val vm = CurrencyViewModel(holder)

        vm.usd = 41.0
        assertEquals(41.0, holder.getRate("USD"))
    }

    @Test
    fun delegate_getValue_returnsZeroWhenMissing() {
        val holder = RateHolder(mutableMapOf<String, Double>())
        val vm = CurrencyViewModel(holder)

        assertEquals(0.0, vm.usd, 0.0001)
    }
}