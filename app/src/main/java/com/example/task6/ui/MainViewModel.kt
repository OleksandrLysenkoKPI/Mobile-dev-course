package com.example.task6.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task6.data.EnergyRepository
import com.example.task6.data.SensorSimulator
import com.example.task6.domain.EnergySample
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val simulator = SensorSimulator(scope = viewModelScope)
    private val repository = EnergyRepository(simulator)

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        simulator.start()

        viewModelScope.launch {
            repository.uiFlow.collect { sample ->
                _uiState.update { current ->
                    val newList = (current.recentSamples + sample).takeLast(200)
                    current.copy(
                        recentSamples = newList,
                        currentPower = sample.powerWatts,
                        lastUpdated = sample.timestamp
                    )
                }
            }
        }
    }

    override fun onCleared() {
        simulator.stop()
        super.onCleared()
    }
}

data class MainUiState(
    val recentSamples: List<EnergySample> = emptyList(),
    val currentPower: Double = 0.0,
    val lastUpdated: Long = 0L
)
