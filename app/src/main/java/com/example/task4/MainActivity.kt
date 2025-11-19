package com.example.task4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task4.ui.theme.Task4Theme
import androidx.compose.ui.Alignment
import androidx.compose.material3.Button
import androidx.compose.material3.*
import androidx.compose.runtime.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Task4Theme {
                CurrencyApp()
            }
        }
    }
}


@Composable
fun CurrencyCard(holder: RateHolder<Double>) {
    val amount = remember { mutableStateOf("") }
    val fromCurrency = remember { mutableStateOf("USD") }
    val toCurrency = remember { mutableStateOf("EUR") }

    val showDialog = remember { mutableStateOf(false) }
    val result = remember { mutableStateOf("") }

    val currencyList = holder.let { listOf("USD", "EUR", "UAH") }

    Card(
        modifier = Modifier
            .width(300.dp)
            .verticalScroll(rememberScrollState()),
        colors = CardDefaults.cardColors(containerColor = Color(0xC688ABF5))
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Currency Converter 💱",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp)
            )

            Spacer(Modifier.height(15.dp))

            // Input fields
            OutlinedTextField(
                value = amount.value,
                onValueChange = { amount.value = it },
                label = { Text("Amount") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            DropdownCurrencySelector(
                label = "From Currency",
                currencies = currencyList,
                selected = fromCurrency
            )

            DropdownCurrencySelector(
                label = "To Currency",
                currencies = currencyList,
                selected = toCurrency
            )


            Spacer(Modifier.height(25.dp))

            Button(
                onClick = {
                    try {
                        val amountDouble = amount.value.toDouble()
                        val res = holder.convert(
                            amountDouble,
                            fromCurrency.value,
                            toCurrency.value
                        )

                        if (res == null) {
                            result.value = "⚠️ Conversion failed. Check currencies."
                        } else {
                            result.value = "Result: $res"
                        }

                        showDialog.value = true
                    } catch (e: Exception) {
                        result.value = "⚠️ Input Error: Please enter valid numbers."
                        showDialog.value = true
                    }
                },
                modifier = Modifier.size(width = 200.dp, height = 50.dp)
            ) {
                Text("Convert")
            }

            if (showDialog.value) {
                DialogWindow(
                    showDialog = showDialog,
                    headline = "💱 Conversion Result",
                    resultText = result.value
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
                    fontWeight = FontWeight.Bold,
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
fun DropdownCurrencySelector(
    label: String,
    currencies: List<String>,
    selected: MutableState<String>
) {
    val expanded = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
        Text(text = label, fontSize = 14.sp)

        Box {
            OutlinedTextField(
                value = selected.value,
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded.value = true }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                }
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                currencies.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(currency) },
                        onClick = {
                            selected.value = currency
                            expanded.value = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun CurrencyApp() {
    val holder = remember {
        RateHolder(
            mutableMapOf(
                "USD" to 42.1,
                "EUR" to 48.7,
                "UAH" to 1.0
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CurrencyCard(holder)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CurrencyCardPreview() {
    CurrencyApp()
}
