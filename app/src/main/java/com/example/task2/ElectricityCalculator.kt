package com.example.task2

class ElectricityCalculator {
    fun kWattConvert(watt: Double): Double {
        return watt / 1000
    }

    data class EnergyResult(
        val energyKWh: Double,
        val cost: Double
    )

    fun calculateEnergyConsumption(
        power: Double,
        capacity: Int,
        usageHours: Int,
        electricityPrice: Double
    ): EnergyResult {
        val usage = power * (capacity / 100.0) * usageHours
        val price = usage * electricityPrice
        return EnergyResult(usage, price);
    }

    fun totalEnergyConsumption(vararg results: EnergyResult): EnergyResult {
        val totalEnergy = results.sumOf { it.energyKWh }
        val totalCost = results.sumOf { it.cost }
        return EnergyResult(totalEnergy, totalCost)
    }
}