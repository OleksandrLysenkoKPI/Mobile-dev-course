package com.example.task5
import com.example.task5.data.SensorGeneratorImpl
import com.example.task5.services.SensorManager
import kotlinx.coroutines.*

fun main() = runBlocking {
    val generator = SensorGeneratorImpl()
    val manager = SensorManager(generator, this)

    manager.addSensor("T-1")
    manager.addSensor("T-2")
    manager.addSensor("V-1")

    manager.start()

    delay(5000)
    manager.stop()

    println("History size: ${manager.history.size}")
    println("Last added values: ${manager.liveValues}")
}