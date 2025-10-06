package com.example.task2

import kotlin.math.pow

class ApplianceSimulation {
    // кг, °C, °C, кВт/год
    fun waterHeater(waterMass: Double, startTemp: Int, endTemp: Int): Double {
        val c = 4180 // Дж/(кг·°C)
        val deltaT = endTemp - startTemp
        val energyJ = waterMass * c * deltaT
        val energyKwh = energyJ / 3.6e6
        return energyKwh
    }

    // кВТ, год, % дня
    fun coolerEnergy(power: Double, hours: Int, dutyCycle: Int = 40): Double {
        return power * (dutyCycle / 100.0) * hours
    }

    // В, Ом
    fun powerFromVoltage(voltage: Double, resistance: Double): Double {
        return voltage.pow(2) / resistance / 1000 // кВТ
    }

    // кг, °C, °C, кВт, год
    fun washingMachineCycle(
        waterMass: Double,
        startTemp: Int,
        endTemp: Int,
        motorPower: Double,
        cycleTime: Double
    ): Double {
        val heatingEnergy = waterHeater(waterMass, startTemp, endTemp)
        val motorEnergy = motorPower * cycleTime
        return heatingEnergy + motorEnergy
    }
}