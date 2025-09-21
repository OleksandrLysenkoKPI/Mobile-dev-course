package com.example.task_1_compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.task1_compose.Screen
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GradientText("Калькулятори")

        Spacer(Modifier.height(32.dp))

        Button(onClick = {
            navController.navigate(route = Screen.FuelCalculator.route)
        }) {
            Text("Калькулятор палива")
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(route = Screen.OilCalculator.route)
        }) {
            Text("Калькулятор мазуту")
        }

    }
}

@Composable
fun GradientText(text: String ) {
    Text(
        text = text,
        fontSize = 30.sp,
        style = androidx.compose.ui.text.TextStyle(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.Red,
                    Color.Magenta,
                    Color.Blue,
                    Color.Cyan,
                    Color.Green,
                    Color.Yellow,
                    Color.Red
                )
            )
        )
    )
}