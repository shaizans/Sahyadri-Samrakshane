package com.sahyadri.sentinel.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object Camera : Screen("camera_screen")
    object ReportDetails : Screen("report_details_screen")
}
