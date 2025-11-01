package com.example.task3.logic

interface Reportable {
    fun generateReport(): String
}

class SmartSystem : Reportable {
    private val meters = mutableListOf<SmartMeter>()

    fun addMeter(meter: SmartMeter) {
        meters.add(meter)
    }

    override fun generateReport(): String {
        return meters.joinToString("\n") {it.getInfo()}
    }
}