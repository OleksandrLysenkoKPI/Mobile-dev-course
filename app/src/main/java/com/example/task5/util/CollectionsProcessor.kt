package com.example.task5.util

import com.example.task5.reading.model.SensorReading
import kotlin.collections.filter
import kotlin.collections.map
import kotlin.collections.sortedByDescending

object CollectionsProcessor {

    fun averageValue(history: List<SensorReading>): Double =
        history.map { it.value }.average()

    fun bySensor(history: List<SensorReading>, id: String): List<SensorReading> =
        history.filter { it.sensorId == id }

    fun topValues(history: List<SensorReading>, count: Int): List<SensorReading> =
        history.sortedByDescending { it.value }.take(count)
}
