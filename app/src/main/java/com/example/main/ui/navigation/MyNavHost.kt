package com.example.main.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.example.main.model.MainViewModel
import com.example.main.ui.screens.EditToDoScreen
import com.example.main.ui.screens.MainScreen
import com.example.main.ui.screens.MyScreens
import com.example.main.ui.screens.TimerScreen


@Composable
fun MyNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Screens via BottomBar und Fullscreens
        composable(MyScreens.Main.route) { MainScreen(viewModel, navController) }
        composable(MyScreens.Timer.route) { TimerScreen(viewModel, navController) }
        composable(MyScreens.EditToDo.route) { EditToDoScreen(viewModel, navController) }

    }
}
