package io.rob.diet

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import io.rob.diet.charts.ChartsUI
import io.rob.diet.compose.ComposeViewModel
import io.rob.diet.measurement.MeasurementUI
import io.rob.diet.progress.ProgressUI
import io.rob.diet.settings.SettingsUI

@Composable
fun MainUI(viewModel: ComposeViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Navigation.PROGRESS.asString) {
        composable(Navigation.PROGRESS.asString) {
            viewModel.fetchRecap()
            ProgressUI(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable("${Navigation.CHART.asString}{type}") { entry ->
            val param = Charts.from(entry.arguments!!.getString("type")!!)
            ChartsUI(param, viewModel)
        }
        composable(Navigation.NEW_MEASUREMENT.asString) {
            MeasurementUI {
                viewModel.saveData(it.toMeasurement())
            }
        }
        composable(Navigation.SETTINGS.asString) {
            SettingsUI()
        }
    }
}

enum class Navigation(val asString: String) {
    PROGRESS("progress"),
    CHART("chart/"),
    NEW_MEASUREMENT("new_measurement"),
    SETTINGS("settings")
}

enum class Charts(val type: String) {
    BMI("bmi"),
    WEIGHT("weight"),
    BODY_FAT("body_fat"),
    HIP("hip"),
    UMBILICAL("umbilical"),
    WAIST("waist");

    companion object {
        fun from(value: String) =
            when (value) {
                "bmi" -> BMI
                "weight" -> WEIGHT
                "body_fat" -> BODY_FAT
                "hip" -> HIP
                "umbilical" -> UMBILICAL
                "waist" -> WAIST
                else -> error("Unsupported value $value")
            }
    }
}

