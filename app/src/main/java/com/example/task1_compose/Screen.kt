package com.example.task1_compose

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object FuelCalculator: Screen(route = "fuel_calculator_screen")
    object OilCalculator: Screen(route = "oil_calculator_screen")
}