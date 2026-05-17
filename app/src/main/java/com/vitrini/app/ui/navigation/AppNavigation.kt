package com.vitrini.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vitrini.app.ui.auth.login.LoginScreen
import com.vitrini.app.ui.auth.register.RegisterBusinessScreen
import com.vitrini.app.ui.auth.register.RegisterTypeScreen
import com.vitrini.app.ui.auth.register.RegisterUserScreen
import com.vitrini.app.ui.auth.start.AuthStartScreen
import com.vitrini.app.ui.feed.FeedScreen
import com.vitrini.app.ui.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH
    ) {
        composable(AppRoutes.SPLASH) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(AppRoutes.AUTH_START) {
                        popUpTo(AppRoutes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.AUTH_START) {
            AuthStartScreen(
                onLoginClick = { navController.navigate(AppRoutes.LOGIN) },
                onRegisterClick = { navController.navigate(AppRoutes.REGISTER_TYPE) }
            )
        }

        composable(AppRoutes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppRoutes.FEED) {
                        popUpTo(AppRoutes.AUTH_START) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.REGISTER_TYPE) {
            RegisterTypeScreen(
                onUserClick = { navController.navigate(AppRoutes.REGISTER_USER) },
                onBusinessClick = { navController.navigate(AppRoutes.REGISTER_BUSINESS) }
            )
        }

        composable(AppRoutes.REGISTER_USER) {
            RegisterUserScreen(
                onRegisterSuccess = {
                    navController.navigate(AppRoutes.FEED) {
                        popUpTo(AppRoutes.AUTH_START) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.REGISTER_BUSINESS) {
            RegisterBusinessScreen(
                onRegisterSuccess = {
                    navController.navigate(AppRoutes.FEED) {
                        popUpTo(AppRoutes.AUTH_START) { inclusive = true }
                    }
                }
            )
        }

        composable(AppRoutes.FEED) {
            FeedScreen()
        }
    }
}