package com.example.i_found.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.i_found.items.ItemsScreen
import com.example.i_found.models.lost
import com.example.i_found.ui.theme.home.HomeScreen
import com.example.i_found.ui.theme.home.ViewItems
import com.example.i_found.ui.theme.login.LoginViewModel
import com.example.i_found.ui.theme.login.SignUpScreen
import com.example.i_found.ui.theme.signup.LoginScreen
import com.example.i_found.ui.theme.splash.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    navController: NavHostController = rememberNavController(),
    startDestination:String = SPLASH_URL
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(LOGIN_URL) {
            LoginScreen(navController = navController,
                loginViewModel = LoginViewModel())
        }
        composable(SIGNUP_URL) {
            SignUpScreen(navController = navController,
                loginViewModel = LoginViewModel())
        }
        composable(HOME_URL) {
            HomeScreen(homeViewModel = null, navController = navController)
        }
        composable(ADD_URL) {
            ItemsScreen(itemViewModel = null, lostId = String(), navController = navController)
        }
        composable(VIEW_URL) {
           ViewItems(lost = lost(), navController = navController)
        }
        composable(SPLASH_URL) {
           SplashScreen(navController = navController)
        }

    }
}
