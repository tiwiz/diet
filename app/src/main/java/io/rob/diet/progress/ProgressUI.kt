package io.rob.diet.progress

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.rob.diet.Charts
import io.rob.diet.Navigation
import io.rob.diet.R
import io.rob.diet.common.Lce
import io.rob.diet.compose.*
import io.rob.diet.ui.theme.DietTheme

@Composable
private fun ElementUI(
    title: String,
    element: RecapElement,
    clickBehaviour: () -> Unit = {}
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
        .clickable {
            clickBehaviour()
        }) {
        Box(
            modifier = Modifier
                .padding(top = 18.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(width = 1.dp, color = Color.LightGray),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(top = 20.dp, bottom = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_history),
                        contentDescription = ""
                    )
                    Text(text = "${element.start}")
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = "",
                        modifier = Modifier.sizeIn(minWidth = 96.dp)
                    )
                    Text(text = "${element.end}")
                    Image(
                        painter = painterResource(id = R.drawable.ic_last_check),
                        contentDescription = ""
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_progress),
                        contentDescription = "",
                        modifier = Modifier.size(width = 64.dp, height = 64.dp)
                    )
                    Text(
                        text = String.format("%.2f", element.delta),
                        fontSize = 48.sp
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            DietSubtitle(
                label = title,
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
            )
        }

    }
}

@Composable
private fun Title(settingsClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        DietTitle(titleRes = R.string.progress_title)
        Image(
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 8.dp)
                .requiredSize(width = 24.dp, height = 24.dp)
                .clickable { settingsClick() }
        )
    }
}

@Composable
private fun RecapUi(ui: ComposeRecapUI, navigation: (String) -> Unit = {}) {

    val order = arrayOf(
        Charts.WEIGHT,
        Charts.BMI,
        Charts.BODY_FAT,
        Charts.HIP,
        Charts.UMBILICAL,
        Charts.WAIST
    )

    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.background,
        darkIcons = MaterialTheme.colors.isLight
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
        contentAlignment = Alignment.BottomEnd
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Title {
                navigation(Navigation.SETTINGS.asString)
            }

            order.forEach { res ->
                val item = ui[res]!!
                ElementUI(title = stringResource(id = item.titleRes), element = item) {
                    navigation("${Navigation.CHART.asString}${res.type}")
                }
            }
        }

        FloatingActionButton(
            onClick = { navigation(Navigation.NEW_MEASUREMENT.asString) },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.background
        ) {
            Image(painter = painterResource(id = R.drawable.ic_new), contentDescription = "")
        }
    }

}


@Composable
fun ProgressUI(navController: NavController, viewModel: ComposeViewModel = viewModel()) {
    val state by viewModel.recap.observeAsState(initial = Lce.Loading)
    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.background,
        darkIcons = MaterialTheme.colors.isLight
    )

    Crossfade(targetState = state) {
        when (it) {
            is Lce.Loading -> LoadingUI()
            is Lce.Success -> if (it.data == null) {
                navController.navigate(Navigation.SETTINGS.asString)
            } else {
                Recap2UI(ui = it.data!!) { destination ->
                    navController.navigate(destination)
                }
            }
            else -> ErrorUI {
                viewModel.fetchComposeRecap()
            }
        }
    }
}

private val previewData = hashMapOf(
    Charts.WEIGHT to RecapElement(titleRes = R.string.weight_hint, start = 5f, end = 6f),
    Charts.BMI to RecapElement(titleRes = R.string.bmi, start = 5f, end = 6f),
    Charts.BODY_FAT to RecapElement(titleRes = R.string.body_fat, start = 5f, end = 6f),
    Charts.HIP to RecapElement(titleRes = R.string.hip, start = 5f, end = 6f),
    Charts.UMBILICAL to RecapElement(titleRes = R.string.umbilical, start = 5f, end = 6f),
    Charts.WAIST to RecapElement(titleRes = R.string.waist, start = 5f, end = 6f),
)

@Preview(
    name = "Day",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun ProgressUI_Day() {
    DietTheme(darkTheme = false) {
        RecapUi(ui = previewData)
    }
}

@Preview(
    name = "Night",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProgressUI_Night() {
    DietTheme(darkTheme = true) {
        RecapUi(ui = previewData)
    }
}