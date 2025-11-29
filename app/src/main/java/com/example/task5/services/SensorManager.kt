package com.example.task5.services

import com.example.task5.reading.SensorGenerator
import com.example.task5.reading.model.SensorReading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SensorManager(
    private val generator: SensorGenerator,
    private val scope: CoroutineScope
) {
    val sensors: MutableSet<String> = mutableSetOf()
    val history: MutableList<SensorReading> = mutableListOf()
    val liveValues: MutableMap<String, SensorReading> = mutableMapOf()

    private val jobs: MutableList<Job> = mutableListOf()

    fun addSensor(sensorId: String) {
        sensors.add(sensorId)
    }

    fun start() {
        sensors.forEach { sensorId ->
            val job = scope.launch {
                while (isActive) {
                    val reading = generator.generate(sensorId)
                    updateCollections(reading)
                    delay(1000)
                }
            }
            jobs.add(job)
        }
    }

    private fun updateCollections(reading: SensorReading) {
        history.add(reading)
        liveValues[reading.sensorId] = reading
    }

    fun stop() {
        jobs.forEach { it.cancel() }
    }
}
