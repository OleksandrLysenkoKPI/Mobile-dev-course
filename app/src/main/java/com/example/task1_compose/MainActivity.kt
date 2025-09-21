package com.example.task1_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.task_1_compose.FuelCalculatorScreen
import com.example.task_1_compose.HomeScreen

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            SetupNavGraph(navHostController = navController)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreensPreview() {
    HomeScreen(navController = rememberNavController())
}






