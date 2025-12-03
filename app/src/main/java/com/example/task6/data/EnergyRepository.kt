package com.example.task6.data

import com.example.task6.domain.EnergySample
import kotlinx.coroutines.flow.*

class EnergyRepository(
    private val simulator: SensorSimulator
) {
    val rawFlow = simulator.channel.consumeAsFlow()

    val validatedFlow: Flow<EnergySample> = rawFlow
        .filter { it.powerWatts >= 0.0 && it.powerWatts < 10000.0 }


    fun averagedFlow(windowSize: Int = 5): Flow<EnergySample> {
        return validatedFlow
            .map { it.powerWatts }
            .scan(mutableListOf<Double>()) { acc, value ->
                acc.add(value)
                if (acc.size > windowSize) acc.removeAt(0)
                acc
            }
            .map { list ->
                val avg = if (list.isEmpty()) 0.0 else list.average()
                EnergySample(simulator.channel.toString(), System.currentTimeMillis(), avg)
            }
    }

    val uiFlow: Flow<EnergySample> = validatedFlow
        .buffer(64)
        .sample(500L)
}