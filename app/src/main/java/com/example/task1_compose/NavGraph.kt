package com.example.task1_compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.task_1_compose.FuelCalculatorScreen
import com.example.task_1_compose.HomeScreen

@Composable
fun SetupNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController, startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navHostController)
        }
        composable(
            route = Screen.FuelCalculator.route
        ) {
            FuelCalculatorScreen()
        }
        composable(
            route = Screen.OilCalculator.route
        ) {
            OilCalculatorScreen()
        }
    }
}