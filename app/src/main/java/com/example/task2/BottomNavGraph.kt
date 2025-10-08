package com.example.task2

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.task2.screens.CalculatorScreen
import com.example.task2.screens.SimulationsScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Calculator.route
    ) {
        composable(route = BottomBarScreen.Calculator.route) {
            CalculatorScreen()
        }
        composable(route = BottomBarScreen.Simulation.route) {
            SimulationsScreen()
        }
    }
}