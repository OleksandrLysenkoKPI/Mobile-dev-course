package com.example.task2

import org.junit.Test

import org.junit.Assert.*

class ElectricityCalculatorTest {

    private val calculator = ElectricityCalculator()

    @Test
    fun testKWattConvert() {
        val result = calculator.kWattConvert(1500.0)
        assertEquals(1.5, result, 0.0001)
    }

    @Test
    fun testCalculateEnergyConsumption() {
        val result = calculator.calculateEnergyConsumption(
            power = 1.5,        // кВт
            capacity = 80,      // 80%
            usageHours = 5,
            electricityPrice = 2.5 // грн/кВт·год
        )

        assertEquals(6.0, result.energyKWh, 0.001)
        assertEquals(15.0, result.cost, 0.001)
    }

    @Test
    fun testTotalEnergyConsumption() {
        val r1 = ElectricityCalculator.EnergyResult(2.0, 6.0)
        val r2 = ElectricityCalculator.EnergyResult(3.0, 9.0)
        val total = calculator.totalEnergyConsumption(r1, r2)

        assertEquals(5.0, total.energyKWh, 0.001)
        assertEquals(15.0, total.cost, 0.001)
    }
}