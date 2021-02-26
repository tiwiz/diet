package io.rob.diet

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.rob.diet.progress.ProgressUI
import io.rob.diet.progress.ProgressViewModel

@Composable
fun MainUI(viewModel: ProgressViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "progress") {
        composable("progress") { ProgressUI(navController = navController, viewModel = viewModel) }
        composable("chart/{type}") { entry ->
            println("Value: ${entry.arguments?.getString("type")}")
        }
    }
}

