package com.example.task6.data

import com.example.task6.domain.EnergySample
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.random.Random

class SensorSimulator(
    private val scope: CoroutineScope,
    private val sensorId: String = "sensor-001",
    private val intervalMs: Long = 1000L // частота генерації
) {
    val channel = Channel<EnergySample>(capacity = Channel.UNLIMITED)

    private var job: Job? = null

    fun start() {
        if (job != null) return
        job = scope.launch(Dispatchers.Default) {
            while (isActive) {
                val sample = EnergySample(
                    sensorId = sensorId,
                    timestamp = System.currentTimeMillis(),
                    powerWatts = simulatePower()
                )
                channel.send(sample)
                delay(intervalMs)
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }

    private fun simulatePower(): Double {
        val base = 200.0 // базове навантаження у ватах
        val variation = 100.0 * kotlin.math.sin(System.currentTimeMillis() / 10000.0)
        val noise = Random.nextDouble(-20.0, 20.0)
        val spike = if (Random.nextDouble() < 0.03) Random.nextDouble(200.0, 800.0) else 0.0
        return (base + variation + noise + spike).coerceAtLeast(0.0)
    }
}