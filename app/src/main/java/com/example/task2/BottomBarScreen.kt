package com.example.task2

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: @Composable () -> ImageVector
) {
    object Calculator: BottomBarScreen(
        route = "calculator",
        title = "Calculator",
        icon = { ImageVector.vectorResource(id = R.drawable.calculate_24px) }
    )
    object Simulation: BottomBarScreen(
        route = "simulation",
        title = "Simulation",
        icon = { ImageVector.vectorResource(id = R.drawable.modeling_24px) }
    )
}