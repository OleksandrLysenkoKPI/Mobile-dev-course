package com.example.task5.reading

import com.example.task5.reading.model.SensorReading

interface SensorGenerator {
    suspend fun generate(sensorId: String): SensorReading
}