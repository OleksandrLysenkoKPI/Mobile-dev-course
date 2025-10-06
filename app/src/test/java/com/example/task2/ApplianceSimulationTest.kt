package com.example.task2

import org.junit.Test
import org.junit.Assert.*

class ApplianceSimulationTest {
    private val sim = ApplianceSimulation()

    @Test
    fun testWaterHeater() {
        val energy = sim.waterHeater(10.0, 20, 60)
        assertEquals(0.465, energy, 0.01)
    }

    @Test
    fun testCoolerEnergy() {
        val energy = sim.coolerEnergy(0.2, 10, 50)
        assertEquals(1.0, energy, 0.0001)
    }

    @Test
    fun testWashingMachineCycle() {
        val energy = sim.washingMachineCycle(
            15.0,
            20,
            60,
            0.5,
            1.5
        )
        assertTrue(energy > 0.5 && energy < 2.0)
    }
}