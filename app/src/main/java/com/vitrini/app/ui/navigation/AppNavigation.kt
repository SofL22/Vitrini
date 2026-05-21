package com.vitrini.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vitrini.app.data.dto.request.BusinessRegisterRequestDto
import com.vitrini.app.data.dto.request.CustomerRegisterRequestDto
import com.vitrini.app.data.dto.request.LoginRequestDto
import com.vitrini.app.data.local.AppDatabase
import com.vitrini.app.data.remote.FakeAuthApi
import com.vitrini.app.data.remote.RemoteAuthDataSource
import com.vitrini.app.repository.AuthRepository
import com.vitrini.app.ui.auth.login.LoginScreen
import com.vitrini.app.ui.auth.register.RegisterBusinessScreen
import com.vitrini.app.ui.auth.register.RegisterTypeScreen
import com.vitrini.app.ui.auth.register.RegisterUserScreen
import com.vitrini.app.ui.auth.start.AuthStartScreen
import com.vitrini.app.ui.feed.FeedScreen
import com.vitrini.app.ui.splash.SplashScreen
import com.vitrini.app.utils.SessionManager

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val authRepository = remember {
        AuthRepository(
            remoteAuthDataSource = RemoteAuthDataSource(FakeAuthApi()),
            sessionManager = SessionManager(context),
            userDao = database.userDao(),
            businessDao = database.businessDao()
        )
    }
    val session by authRepository.observeSession().collectAsState(
        initial = com.vitrini.app.data.local.preferences.SessionData(
            null,
            null,
            null,
            false
        )
    )

    var profile by remember { mutableStateOf(AuthRepository.CurrentUserProfile(null, null, null)) }

    LaunchedEffect(session.userId, session.isLoggedIn) {
        profile = if (session.isLoggedIn && session.userId != null) {
            authRepository.getCurrentUserProfile(session.userId!!)
        } else {
            AuthRepository.CurrentUserProfile(null, null, null)
        }
    }

    NavHost(navController = navController, startDestination = AppRoutes.SPLASH) {
        composable(AppRoutes.SPLASH) {
            SplashScreen(onSplashFinished = {
                val nextRoute = if (session.isLoggedIn) AppRoutes.FEED else AppRoutes.AUTH_START
                navController.navigate(nextRoute) { popUpTo(AppRoutes.SPLASH) { inclusive = true } }
            })
        }

        composable(AppRoutes.AUTH_START) {
            AuthStartScreen(
                onLoginClick = { navController.navigate(AppRoutes.LOGIN) },
                onRegisterClick = { navController.navigate(AppRoutes.REGISTER_TYPE) })
        }

        composable(AppRoutes.LOGIN) {
            LoginScreen(
                onLoginClick = { email, password ->
                    runCatching {
                        kotlinx.coroutines.runBlocking {
                            authRepository.login(
                                LoginRequestDto(
                                    email,
                                    password
                                )
                        )
                    }
                }
                        .fold(onSuccess = {
                            navController.navigate(AppRoutes.FEED) {
                                popUpTo(AppRoutes.AUTH_START) {
                                    inclusive = true
                                }

                            }
                            null
                        }, onFailure = { it.message ?: "Login failed" })
                },
                onBackClick = { navController.popBackStack() },
                onRegisterClick = { navController.navigate(AppRoutes.REGISTER_TYPE) }
            )
        }

        composable(AppRoutes.REGISTER_TYPE) {
            RegisterTypeScreen(
                onUserClick = { navController.navigate(AppRoutes.REGISTER_USER) },
                onBusinessClick = { navController.navigate(AppRoutes.REGISTER_BUSINESS) },
                onBackClick = { navController.popBackStack() },
                onLoginClick = { navController.navigate(AppRoutes.LOGIN) }
            )
        }

        composable(AppRoutes.REGISTER_USER) {
            RegisterUserScreen(
                onRegister = { fullName, email, password, confirmPassword ->
                    runCatching {
                        kotlinx.coroutines.runBlocking {
                            authRepository.registerCustomer(
                                CustomerRegisterRequestDto(
                                    fullName,
                                    email,
                                    password,
                                    confirmPassword
                                )
                            )

                        }
                    }
                        .fold(onSuccess = {
                            navController.navigate(AppRoutes.FEED) {
                                popUpTo(AppRoutes.AUTH_START) {
                                    inclusive = true
                                }
                            }
                            null
                        }, onFailure = { it.message ?: "Register failed" })
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.REGISTER_BUSINESS) {
            RegisterBusinessScreen(
                onRegister = { ownerName, businessName, email, password, confirmPassword ->
                    runCatching {
                        kotlinx.coroutines.runBlocking {
                            authRepository.registerBusiness(
                                BusinessRegisterRequestDto(
                                    ownerName,
                                    businessName,
                                    email,
                                    password,
                                    confirmPassword
                                )
                            )

                        }
                    }
                        .fold(onSuccess = {
                            navController.navigate(AppRoutes.FEED) {
                                popUpTo(AppRoutes.AUTH_START) {
                                    inclusive = true
                                }
                            }
                            null
                        }, onFailure = { it.message ?: "Register failed" })
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoutes.FEED) {
            if (session.isLoggedIn) {
                FeedScreen(
                    userName = profile.name,
                    userEmailOrId = profile.emailOrId,
                    businessName = profile.businessName,
                    onLogout = {
                        authRepository.logout()
                        navController.navigate(AppRoutes.AUTH_START) {
                            popUpTo(AppRoutes.FEED) {
                                inclusive = true
                            }
                        }
                    }
                )
            } else {
                navController.navigate(AppRoutes.AUTH_START) {
                    popUpTo(AppRoutes.FEED) {
                        inclusive = true
                    }
                }
            }
        }
    }
}