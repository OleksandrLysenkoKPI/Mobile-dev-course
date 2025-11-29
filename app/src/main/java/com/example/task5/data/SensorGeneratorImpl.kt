package com.example.task5.data

import com.example.task5.reading.SensorGenerator
import com.example.task5.reading.model.SensorReading
import kotlinx.coroutines.delay

class SensorGeneratorImpl : SensorGenerator {
    override suspend fun generate(sensorId: String): SensorReading {
        delay(200) // симуляція затримки
        return SensorReading(
            sensorId = sensorId,
            timestamp = System.currentTimeMillis(),
            value = (0..1000).random() / 100.0
        )
    }
}
