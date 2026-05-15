package com.sahyadri.sentinel.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object Camera : Screen("camera_screen")
    object ReportDetails : Screen("report_details_screen")
    
    // Bottom Nav Screens
    object Dash : Screen("dash_screen")
    object Reports : Screen("reports_screen")
    object Profile : Screen("profile_screen")
    object LocationPreview : Screen("location_preview_screen")
}
