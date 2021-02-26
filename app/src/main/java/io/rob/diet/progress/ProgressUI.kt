package io.rob.diet.progress

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
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
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import io.rob.diet.R
import io.rob.diet.common.Lce
import io.rob.diet.ui.theme.DietTheme
import java.util.*

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
            Text(
                title,
                modifier = Modifier
                    .background(MaterialTheme.colors.background),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h2
            )
        }

    }
}

@Composable
private fun RecapUi(ui: ComposeRecapUI, navigation: (String) -> Unit = {}) {

    val order = arrayOf(
        R.string.weight_hint,
        R.string.bmi,
        R.string.body_fat,
        R.string.hip,
        R.string.umbilical,
        R.string.waist
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
            Text(
                text = stringResource(id = R.string.progress_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h1
            )

            order.forEach { res ->
                val title = stringResource(id = res)
                ElementUI(title = title, element = ui[res]!!) {
                    navigation("chart/${title.toLowerCase(Locale.getDefault())}")
                }
            }
        }

        FloatingActionButton(
            onClick = { navigation("newMeasurement") },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.background
        ) {
            Image(painter = painterResource(id = R.drawable.ic_new), contentDescription = "")
        }
    }

}


@Composable
fun ProgressUI(navController: NavController, viewModel: ProgressViewModel) {
    val state by viewModel.composeRecap.observeAsState(initial = Lce.Loading)

    Crossfade(targetState = state) {
        when (it) {
            is Lce.Loading -> Box {}
            is Lce.Success -> RecapUi(ui = it.data) { destination ->
                navController.navigate(destination)
            }
            else -> Box {}
        }
    }

    if (state !is Lce.Success) {
        viewModel.fetchComposeRecap()
    }
}

private val previewData = hashMapOf(
    R.string.weight_hint to RecapElement(start = 5f, end = 6f),
    R.string.bmi to RecapElement(start = 5f, end = 6f),
    R.string.body_fat to RecapElement(start = 5f, end = 6f),
    R.string.hip to RecapElement(start = 5f, end = 6f),
    R.string.umbilical to RecapElement(start = 5f, end = 6f),
    R.string.waist to RecapElement(start = 5f, end = 6f),
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