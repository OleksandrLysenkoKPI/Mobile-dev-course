package com.example.task3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task3.ui.theme.Task3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Task3App()
        }
    }
}

@Composable
fun Task3App() {
    Task3Theme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            MetersCarousel()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetersCarousel() {
    // Список карток (список Composable функцій)
    val meters = remember {
        listOf<@Composable () -> Unit>(
            { ElectricMeterCard() },
            { WaterMeterCard() }
        )
    }

    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { meters.size },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 24.dp),
        preferredItemWidth = 300.dp,
        itemSpacing = 16.dp,
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) { index ->
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .maskClip(MaterialTheme.shapes.extraLarge)
        ) {
            meters[index]() // Виклик відповідної картки
        }
    }
}

@Composable
fun ElectricMeterCard() {
    val fields = remember { mutableStateListOf("", "", "", "") }
    val labels = listOf(
        "☀️ Day Tariff",
        "🌙 Night Tariff",
        "🌅 Day Usage",
        "🌃 Night Usage"
    )

    val showDialog = remember { mutableStateOf(false) }
    val resultText = remember { mutableStateOf("") }

    // Картка з полями
    Card(
        modifier = Modifier
            .width(300.dp)
            .verticalScroll(rememberScrollState()),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF60ccc2))
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "Electric Meter ⚡",
                fontSize = 24.sp,
                fontWeight = Bold
            )
            Spacer(Modifier.height(15.dp))

            InputFields(fields, labels)

            Spacer(Modifier.height(30.dp))

            Button(
                onClick = {
                    try {
                        resultText.value = calculateElectricity(fields)
                        showDialog.value = true
                    } catch (e: Exception) {
                        resultText.value = "⚠️ Input Error: Please check accuracy of your input."
                        showDialog.value = true
                    }
                },
                modifier = Modifier.size(width = 200.dp, height = 50.dp)
            ) {
                Text("Calculate")
            }

            // Діалогове вікно з відповідями
            if(showDialog.value) {
                DialogWindow(
                    showDialog = showDialog,
                    headline = "⚡ Electric Meter Report",
                    resultText = resultText.value
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterMeterCard() {
    val fields = remember { mutableStateListOf("", "") }
    val labels = listOf(
        "🪙 Water Tariff",
        "🚿 Water Usage"
    )

    val showDialog = remember { mutableStateOf(false) }
    val resultText = remember { mutableStateOf("") }

    // Картка з полями
    Card(
        modifier = Modifier
            .width(300.dp)
            .verticalScroll(rememberScrollState()),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF75b855))
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = "Water Meter 💧",
                fontSize = 24.sp,
                fontWeight = Bold
            )
            Spacer(Modifier.height(15.dp))

            InputFields(fields, labels)

            Spacer(Modifier.height(30.dp))

            Button(
                onClick = {
                    try {
                        resultText.value = calculateWater(fields)
                        showDialog.value = true
                    } catch (e: Exception) {
                        resultText.value = "⚠️ Input Error: Please check accuracy of your input."
                        showDialog.value = true
                    }
                },
                modifier = Modifier.size(width = 200.dp, height = 50.dp)
            ) {
                Text("Calculate")
            }

            // Діалогове вікно з відповідями
            if (showDialog.value) {
                DialogWindow(
                    showDialog = showDialog,
                    headline = "💧 Water Meter Report",
                    resultText = resultText.value
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogWindow(
    showDialog: MutableState<Boolean>,
    headline: String,
    resultText: String
) {
    BasicAlertDialog(
        onDismissRequest = { showDialog.value = false }
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 6.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = headline,
                    fontWeight = Bold,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(12.dp))

                Text(resultText)

                Spacer(Modifier.height(20.dp))

                Button(onClick = { showDialog.value = false }) {
                    Text("OK")
                }
            }
        }
    }
}

@Composable
fun InputFields(fields: MutableList<String>, labels: List<String>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (index in fields.indices) {
            OutlinedTextField(
                value = fields[index],
                onValueChange = { fields[index] = it },
                label = { Text(labels[index]) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ElectricMeterCardPreview() {
    Task3App()
}