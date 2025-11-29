package com.example.task5.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task5.data.SensorGeneratorImpl
import com.example.task5.reading.model.SensorReading
import com.example.task5.services.SensorManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SensorViewModel : ViewModel() {

    private val generator = SensorGeneratorImpl()
    private val manager = SensorManager(generator, viewModelScope)

    private val _liveValues = MutableStateFlow<Map<String, SensorReading>>(emptyMap())
    val liveValues = _liveValues.asStateFlow()

    private val _history = MutableStateFlow<List<SensorReading>>(emptyList())
    val history = _history.asStateFlow()

    init {
        manager.addSensor("T-1")
        manager.addSensor("T-2")
        manager.addSensor("V-1")

        manager.start()

        viewModelScope.launch {
            while (true) {
                _liveValues.value = manager.liveValues.toMap()
                _history.value = manager.history.toList()
                delay(200)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        manager.stop()
    }
}