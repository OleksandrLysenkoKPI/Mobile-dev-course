package com.example.task6.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.example.task6.domain.EnergySample
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.collectAsState

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Мобільна система енерговитрат",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Поточна потужність: ${"%.1f".format(state.currentPower)} W",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(12.dp))

        Sparkline(samples = state.recentSamples)

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Останні вимірювання",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        SamplesList(samples = state.recentSamples)
    }
}

@Composable
fun Sparkline(samples: List<EnergySample>) {
    val points = samples.map { it.powerWatts }
    val maxV = (points.maxOrNull() ?: 1.0).coerceAtLeast(1.0)
    val minV = points.minOrNull() ?: 0.0
    val range = (maxV - minV).takeIf { it > 0 } ?: 1.0

    Box(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (points.size < 2) return@Canvas

            val w = size.width
            val h = size.height
            val stepX = w / (points.size - 1)

            val path = Path()
            points.forEachIndexed { i, value ->
                val x = i * stepX
                val y = h - ((value - minV) / range * h).toFloat()
                if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }

            drawPath(
                path = path,
                color = Color(0xFF0077CC),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
            )
        }
    }
}

@Composable
fun SamplesList(samples: List<EnergySample>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        items(samples.reversed(), key = { it.timestamp }) { sample ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = "Sensor: ${sample.sensorId}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Time: ${java.util.Date(sample.timestamp)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Text(
                    text = "${"%.1f".format(sample.powerWatts)} W",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Divider()
        }
    }
}
