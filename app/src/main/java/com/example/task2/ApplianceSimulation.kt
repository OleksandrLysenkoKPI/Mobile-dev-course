package com.example.task2

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

    // В, A
    fun powerFromVoltage(voltage: Double, current: Double): Double {
        return (voltage * current) / 1000 // кВт
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