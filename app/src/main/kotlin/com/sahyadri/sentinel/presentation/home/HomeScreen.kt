package com.sahyadri.sentinel.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sahyadri.sentinel.presentation.navigation.Screen
import com.sahyadri.sentinel.presentation.report.ReportsListScreen
import com.sahyadri.sentinel.presentation.profile.ProfileScreen

@Composable
fun HomeScreen(
    onNavigateToCamera: (String) -> Unit,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem("Dashboard", Screen.Dash.route, Icons.Default.Dashboard),
        BottomNavItem("My Reports", Screen.Reports.route, Icons.Default.ListAlt),
        BottomNavItem("Profile", Screen.Profile.route, Icons.Default.AccountCircle),
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dash.route) {
                DashboardScreen(onCategoryClick = { category -> 
                    onNavigateToCamera(category.id)
                })
            }
            composable(Screen.Reports.route) {
                ReportsListScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
