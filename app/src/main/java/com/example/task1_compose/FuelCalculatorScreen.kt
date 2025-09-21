package com.example.task_1_compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.H
import androidx.compose.ui.input.key.Key.Companion.W
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun FuelCalculatorScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val fields = remember { mutableStateListOf("", "", "", "", "", "", "") }
        val labels = listOf("H^P", "C^P", "S^P", "N^P", "O^P", "W^P", "A^P")


        var results by remember { mutableStateOf<FuelResults?>(null) }
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
            if (fields.any {it.isBlank()}) {
                errorMessage = "Заповність усі поля"
                results = null
            } else {
                errorMessage = null
                val nums = fields.map {it.toDouble()}
                val H = nums[0]; val C = nums[1]; val S = nums[2]
                val N = nums[3]; val O = nums[4]; val W = nums[5]; val A = nums[6]

                results = calculateFuel(H, C, S, N, O, W, A)
            }

        }) {
            Text("Обрахувати")
        }

        Spacer(modifier = Modifier.height(24.dp))

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        results?.let {
            FuelAnswerFields(it)
        }
    }
}

data class FuelResults(
    val K_pc: Double, val K_pg: Double,
    val H_c: Double, val O_c: Double, val N_c: Double, val A_c: Double, val S_c: Double, val C_c: Double,
    val H_g: Double, val O_g: Double, val N_g: Double, val S_g: Double, val C_g: Double,
    val Q_ph: Double, val Q_ch: Double, val Q_gh: Double,
    val error: String? = null
)

fun calculateFuel(H: Double, C: Double, S: Double, N: Double, O: Double, W: Double, A: Double): FuelResults {
    val tolerance = 0.01
    val K_pc = 100 / (100 - W)
    val K_pg = 100 / (100 - W - A)

    val H_c = H * K_pc
    val O_c = O * K_pc
    val N_c = N * K_pc
    val A_c = A * K_pc
    val S_c = S * K_pc
    val C_c = C * K_pc

    val C_g = C * K_pg
    val O_g = O * K_pg
    val N_g = N * K_pg
    val S_g = S * K_pg
    val H_g = H * K_pg

    val Q_ph = (339 * C + 1030 * H - 108.8 * (O - S) - 25 * W) / 1000
    val Q_ch = (Q_ph + 0.025 * W) * (100 / (100 - W))
    val Q_gh = (Q_ph + 0.025 * W) * (100 / (100 - W - A))

    return when {
        abs(H + C + S + N + O + W + A - 100) > tolerance ->
            FuelResults(0.0, 0.0, 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0, 0.0, error = "Елементарний склад РОБОЧОГО палива не дорівнює 100%")
        abs(H_c + C_c + O_c + N_c + A_c + S_c - 100) > tolerance ->
            FuelResults(0.0, 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0, 0.0, error = "Елементарний склад СУХОГО палива не дорівнює 100%")
        abs(H_g + C_g + O_g + N_g + S_g - 100) > tolerance ->
            FuelResults(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0, 0.0, error = "Елементарний склад ГОРЮЧОГО палива не дорівнює 100%")
        else ->
            FuelResults(K_pc, K_pg, H_c, O_c, N_c, A_c, S_c, C_c, H_g, O_g, N_g, S_g, C_g, Q_ph, Q_ch, Q_gh)
    }
}

@Composable
fun FuelAnswerFields(results: FuelResults) {
    val labels = listOf(
        "Коефіцієнт переходу від робочої до сухої маси (K^PC) = ${results.K_pc.toFixed()} %",
        "Коефіцієнт переходу від робочої до горючої маси (K^PГ) = ${results.K_pg.toFixed()} %",
        "Склад сухої маси: H^C=${results.H_c.toFixed()}%, O^C=${results.O_c.toFixed()}%, N^C=${results.N_c.toFixed()}%, A^C=${results.A_c.toFixed()}%, S^C=${results.S_c.toFixed()}%, C^C=${results.C_c.toFixed()}%",
        "Склад горючої маси: H^Г=${results.H_g.toFixed()}%, O^Г=${results.O_g.toFixed()}%, N^Г=${results.N_g.toFixed()}%, S^Г=${results.S_g.toFixed()}%, C^Г=${results.C_g.toFixed()}%",
        "Нижча теплота згоряння для робочої маси (Q^P_H) = ${results.Q_ph.toFixed()} МДж/кг",
        "Нижча теплота згоряння для сухої маси (Q^C_H) = ${results.Q_ch.toFixed()} МДж/кг",
        "Нижча теплота згоряння для горючої маси (Q^Г_H) = ${results.Q_gh.toFixed()} МДж/кг"
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