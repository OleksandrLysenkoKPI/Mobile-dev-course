package com.example.task5.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.task5.reading.model.SensorReading
import com.example.task5.util.CollectionsProcessor
import com.example.task5.viewmodel.SensorViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SensorScreen(
    modifier: Modifier = Modifier,
    viewModel: SensorViewModel = viewModel()
) {
    val liveValues by viewModel.liveValues.collectAsState(initial = emptyMap())
    val history by viewModel.history.collectAsState(initial = emptyList())

    val tabs = listOf("Live", "History", "Analytics")
    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = modifier.padding(16.dp)) {

        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTab == index,
                    onClick = { selectedTab = index }
                )
            }
        }

        when (selectedTab) {
            0 -> LiveTab(liveValues)
            1 -> HistoryTab(history)
            2 -> AnalyticsTab(history)
        }
    }
}

@Composable
fun LiveTab(liveValues: Map<String, SensorReading>) {
    LazyColumn {
        items(liveValues.entries.toList()) { (id, reading) ->
            SensorItem(id, reading.value)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryTab(history: List<SensorReading>) {
    LazyColumn {
        items(history) { reading ->
            Text(
                text = "${reading.sensorId} → ${reading.value} @ ${formatTimestamp(reading.timestamp)}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatTimestamp(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        .withZone(ZoneId.systemDefault())
    return formatter.format(Instant.ofEpochMilli(timestamp))
}

@Composable
fun AnalyticsTab(history: List<SensorReading>) {
    val avg = if (history.isNotEmpty()) CollectionsProcessor.averageValue(history) else 0.0
    val top5 = CollectionsProcessor.topValues(history, 5)

    Column(modifier = Modifier.padding(12.dp)) {
        Text(
            text = "Average value: $avg",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Top 5 values:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 12.dp)
        )

        LazyColumn {
            items(top5) { item ->
                Text(
                    text = "${item.sensorId} → ${item.value}",
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun SensorItem(sensorId: String, value: Double) {
    Text(
        text = "$sensorId → $value",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}
