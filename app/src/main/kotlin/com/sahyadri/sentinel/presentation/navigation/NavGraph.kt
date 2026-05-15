package com.sahyadri.sentinel.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sahyadri.sentinel.presentation.auth.AuthViewModel
import com.sahyadri.sentinel.presentation.auth.LoginScreen
import com.sahyadri.sentinel.presentation.auth.RegisterScreen
import com.sahyadri.sentinel.presentation.camera.CameraScreen
import com.sahyadri.sentinel.presentation.home.HomeScreen
import com.sahyadri.sentinel.presentation.location.LocationPreviewScreen
import com.sahyadri.sentinel.presentation.report.ReportFormScreen

@Composable
fun NavGraph(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val startDestination = remember {
        if (authViewModel.isUserLoggedIn()) Screen.Home.route else Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToCamera = { categoryId ->
                    navController.navigate(Screen.Camera.route + "?categoryId=$categoryId")
                }
            )
        }
        composable(route = Screen.Camera.route + "?categoryId={categoryId}") { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: "General"
            CameraScreen(onImageCaptured = { uri ->
                val encodedUri = java.net.URLEncoder.encode(uri.toString(), "UTF-8")
                navController.navigate(Screen.ReportForm.route + "/$categoryId/$encodedUri")
            })
        }
        composable(route = Screen.ReportForm.route + "/{categoryId}/{imageUri}") { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: "General"
            val imageUriStr = backStackEntry.arguments?.getString("imageUri") ?: ""
            ReportFormScreen(
                imageUri = android.net.Uri.parse(imageUriStr),
                categoryId = categoryId,
                onSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onCancel = { navController.popBackStack() }
            )
        }
        composable(route = Screen.LocationPreview.route) {
            LocationPreviewScreen()
        }
    }
}
