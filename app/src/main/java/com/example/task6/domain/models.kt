package com.example.task6.domain

data class EnergySample(
    val sensorId: String,
    val timestamp: Long, // epoch millis
    val powerWatts: Double // миттєва потужність (W)
)
