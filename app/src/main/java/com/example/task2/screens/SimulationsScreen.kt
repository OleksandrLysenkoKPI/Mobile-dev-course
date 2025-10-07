package com.example.task2.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.task2.ApplianceSimulation
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SimulationsScreen() {
    val simulation = remember { ApplianceSimulation() }

    val applianceOptions = listOf("Water Heater", "Cooler", "Washing Machine", "Power from Voltage")
    var selected by remember { mutableStateOf<String?>(null) }

    val fields = remember { mutableStateListOf("", "", "", "", "") }
    var result by remember { mutableStateOf<Double?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Applience Simulation", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        // Список карток вибору приладів
        applianceOptions.forEach { name ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        selected = name
                        for (i in fields.indices) {
                            fields[i] = ""
                        }
                    },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selected == name) Color(0xFFBBDEFB) else Color.White
                )
            ) {
                Text(
                    text = name,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Динамічні поля, в залежності від вибору
        when (selected) {
            "Water Heater" -> {
                val labels = listOf(
                    "Water mass (kg)",
                    "Start temp (°C)",
                    "End temp (°C)"
                )
                InputFields(fields, labels)
                Button(onClick = {
                    val energy = simulation.waterHeater(
                        waterMass = fields[0].toDoubleOrNull() ?: 0.0,
                        startTemp = fields[1].toIntOrNull() ?: 0,
                        endTemp = fields[2].toIntOrNull() ?: 0
                    )
                    result = energy
                    showDialog = true
                }) { Text("Calculate") }
            }

            "Cooler" -> {
                val labels = listOf(
                    "Power (kW)",
                    "Hours",
                    "Duty Cycle (%)"
                )
                InputFields(fields, labels)
                Button(onClick = {
                    val energy = simulation.coolerEnergy(
                        power = fields[0].toDoubleOrNull() ?: 0.0,
                        hours = fields[1].toIntOrNull() ?: 0,
                        dutyCycle = fields[2].toIntOrNull() ?: 40
                    )
                    result = energy
                    showDialog = true
                }) { Text("Calculate") }
            }

            "Washing Machine" -> {
                val labels = listOf(
                    "Water mass (kg)",
                    "Start temp",
                    "End temp",
                    "Motor power (kW)",
                    "Cycle time (h)"
                )
                InputFields(fields, labels)
                Button(onClick = {
                    val energy = simulation.washingMachineCycle(
                        waterMass = fields[0].toDoubleOrNull() ?: 0.0,
                        startTemp = fields[1].toIntOrNull() ?: 0,
                        endTemp = fields[2].toIntOrNull() ?: 0,
                        motorPower = fields[3].toDoubleOrNull() ?: 0.0,
                        cycleTime = fields[4].toDoubleOrNull() ?: 0.0
                    )
                    result = energy
                    showDialog = true
                }) { Text("Calculate") }
            }

            "Power from Voltage" -> {
                val labels = listOf(
                    "Voltage (W)",
                    "Current (A)"
                )
                InputFields(fields, labels)
                Button(onClick = {
                    val energy = simulation.powerFromVoltage(
                        voltage = fields[0].toDoubleOrNull() ?: 0.0,
                        current = fields[1].toDoubleOrNull() ?: 0.0
                    )
                    result = energy
                    showDialog = true
                }) { Text("Calculate") }
            }
        }
    }

    // Діалогове вікно з результатами
    if (showDialog && result != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("Results") },
            text = {
                Column {
                    Text("Energy Usage: ${"%.2f".format(result)} kWh")
                }
            }
        )
    }
}

@Composable
fun InputFields(fields: MutableList<String>, labels: List<String>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        labels.forEachIndexed { index, label ->
            OutlinedTextField(
                value = fields[index],
                onValueChange = { fields[index] = it },
                label = { Text(label) },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 4.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
    }
}