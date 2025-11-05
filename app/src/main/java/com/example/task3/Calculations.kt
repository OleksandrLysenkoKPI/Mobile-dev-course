package com.example.task3

import com.example.task3.logic.ElectricMeter
import com.example.task3.logic.SmartSystem
import com.example.task3.logic.WaterMeter

/** Точка доступу до логіки електролічильника.
    Приймає дані з інтерфейсу, і повертає звіт
 **/
fun calculateElectricity(fields: List<String>): String {
    val meters = SmartSystem()
    val eMeter = ElectricMeter(
        dayTariff = fields[0].toDouble(),
        nightTariff = fields[1].toDouble()
    )

    meters.addMeter(eMeter)
    eMeter.measureDayUsage(fields[2].toDouble())
    eMeter.measureNightUsage(fields[3].toDouble())

    return meters.generateReport()
}

/** Точка доступу до логіки лічильника води.
    Приймає дані з інтерфейсу, і повертає звіт
 **/
fun calculateWater(fields: List<String>): String {
    val meters = SmartSystem()
    val wMeter = WaterMeter(tariff = fields[0].toDouble())
    meters.addMeter(wMeter)
    wMeter.measureUsage(amount = fields[1].toDouble())

    return meters.generateReport()
}