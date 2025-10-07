package com.example.task2.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.task2.ElectricityCalculator

@Composable
fun CalculatorScreen() {
    val fields = remember { mutableStateListOf("", "", "", "") }
    val labels = listOf(
        "Power (kWh)",
        "Capacity (%)",
        "Usage hours",
        "Electricity Price (₴)"
    )
    val calculator = remember { ElectricityCalculator() }
    var result by remember { mutableStateOf<ElectricityCalculator.EnergyResult?>(null) }
    var showDialog by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Картка з полями
        Card(
            modifier = Modifier
                .width(300.dp)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (index in fields.indices) {
                    OutlinedTextField(
                        value = fields[index],
                        onValueChange = { fields[index] = it },
                        label = { Text(labels[index]) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 250.dp)
                            .padding(vertical = 6.dp)
                    )
                }

                Spacer(Modifier.height(40.dp))

                Button(
                    onClick = {
                        result = calculateEnergyFromFields(fields, calculator)
                        showDialog = true
                    },
                    modifier = Modifier
                        .widthIn(max = 250.dp)
                        .fillMaxWidth()
                ) {
                    Text("Calculate")
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Start"
                    )

                }
            }
        }

        // Діалогове вікно з результатами
        if (showDialog && result != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(onClick = {showDialog = false}) {
                        Text("OK")
                    }
                },
                title = {Text("Results")},
                text = {
                    Column {
                        Text("Energy Usage: ${"%.2f".format(result!!.energyKWh)} kWh")
                        Text("Cost: ${"%.2f".format(result!!.cost)} ₴")
                    }
                }
            )
        }
    }
}


fun calculateEnergyFromFields(
    fields: List<String>,
    calculator: ElectricityCalculator
): ElectricityCalculator.EnergyResult {
    val power = fields[0].toDoubleOrNull() ?: 0.0
    val capacity = fields[1].toIntOrNull() ?: 0
    val hours = fields[2].toIntOrNull() ?: 0
    val price = fields[3].toDoubleOrNull() ?: 0.0

    return calculator.calculateEnergyConsumption(power, capacity, hours, price)
}