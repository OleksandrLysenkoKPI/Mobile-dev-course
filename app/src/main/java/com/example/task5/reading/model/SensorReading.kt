package com.example.task5.reading.model

data class SensorReading(
    val sensorId: String,
    val timestamp: Long,
    val value: Double
)