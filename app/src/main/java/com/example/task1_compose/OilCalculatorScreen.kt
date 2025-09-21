package com.example.task1_compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OilCalculatorScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val fields = remember { mutableStateListOf("", "", "", "", "", "", "", "") }
        val labels = listOf(
            "C, %",
            "H, %",
            "O, %",
            "S, %",
            "Нижча теплота згоряння, МДж/кг",
            "Вологість, %",
            "Зольність сухої маси, %",
            "Ванадій, мг/кг"
        )

        var results by remember { mutableStateOf<OilResults?>(null) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        fields.forEachIndexed { index, value ->
            TextField(
                value = value,
                onValueChange = { fields[index] = it.filter { ch -> ch.isDigit() || ch == '.' } },
                label = { Text(labels[index]) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(onClick = {
            if (fields.any { it.isBlank() }) {
                errorMessage = "Заповніть усі поля!"
                results = null
            } else {
                errorMessage = null
                val nums = fields.map { it.toDouble() }
                val C = nums[0]; val H = nums[1]; val O = nums[2]
                val S = nums[3]; val Q = nums[4]; val V = nums[5]
                val A = nums[6]; val W = nums[7]

                results = calculateOil(C, H, O, S, Q, V, A, W)
            }
        }) {
            Text("Обрахувати")
        }

        Spacer(modifier = Modifier.height(24.dp))

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        results?.let {
            OilAnswerFields(it)
        }
    }
}

data class OilResults(
    val H_r: Double,
    val O_r: Double,
    val W_r: Double,
    val A_r: Double,
    val S_r: Double,
    val C_r: Double,
    val Q_r: Double
)

fun calculateOil(C: Double, H: Double, O: Double, S: Double, Q: Double, V: Double, A: Double, W: Double): OilResults {
    val eq1 = (100 - V - A) / 100.0
    val eq2 = (100 - V) / 100.0

    val C_r = C * eq1
    val H_r = H * eq1
    val O_r = O * eq1
    val S_r = S * eq1
    val A_r = A * eq2
    val W_r = W * eq2

    val Q_r = Q * ((100 - V - A_r) / 100.0) - 0.025 * V

    return OilResults(
        H_r = H_r,
        O_r = O_r,
        W_r = W_r,
        A_r = A_r,
        S_r = S_r,
        C_r = C_r,
        Q_r = Q_r
    )
}


@Composable
fun OilAnswerFields(results: OilResults) {
    val labels = listOf(
        "Склад робочої маси мазуту: H^P = ${results.H_r.toFixed()}%, " +
                "O^P = ${results.O_r.toFixed()}%, " +
                "V^P = ${results.W_r.toFixed()} мг/кг, " +
                "A^P = ${results.A_r.toFixed()}%, " +
                "S^P = ${results.S_r.toFixed()}%, " +
                "C^P = ${results.C_r.toFixed()}%",
        "Нижча теплота згоряння мазуту для робочої маси (Q^r_i) = ${results.Q_r.toFixed()} МДж/кг"
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        labels.forEach { label ->
            Text(text = label)
        }
    }
}

fun Double.toFixed(digits: Int = 2): String = "%.${digits}f".format(this)