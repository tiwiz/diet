package io.rob.diet

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.rob.diet.compose.ComposeViewModel
import io.rob.diet.progress.ProgressUI

@Composable
fun MainUI(viewModel: ComposeViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Navigation.PROGRESS.asString) {
        composable(Navigation.PROGRESS.asString) { ProgressUI(navController = navController, viewModel = viewModel) }
        composable("${Navigation.CHART.asString}{type}") { entry ->
            println("Value: ${entry.arguments?.getString("type")}")
        }
        composable(Navigation.NEW_MEASUREMENT.asString) {
            println("New measurement")
        }
    }
}

enum class Navigation(val asString: String) {
    PROGRESS("progress"),
    CHART("chart/"),
    NEW_MEASUREMENT("new_measurement")

}

enum class Charts(val type: String) {
    BMI("bmi"),
    WEIGHT("weight"),
    BODY_FAT("body_fat"),
    HIP("hip"),
    UMBILICAL("umbilical"),
    WAIST("waist")
}

