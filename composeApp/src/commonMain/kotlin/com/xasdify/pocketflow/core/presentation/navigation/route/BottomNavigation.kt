package com.xasdify.pocketflow.core.presentation.navigation.route

sealed class BottomNavigation(val path: String) {
    data object Home : BottomNavigation("Home_screen")

}