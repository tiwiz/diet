package io.rob.diet.progress

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.transform.CircleCropTransformation
import dev.chrisbanes.accompanist.coil.CoilImage
import io.rob.diet.Charts
import io.rob.diet.Navigation
import io.rob.diet.R
import io.rob.diet.common.user
import io.rob.diet.settings.PLACEHOLDER_URL
import io.rob.diet.ui.theme.DietTheme
import java.util.*

@Composable
fun Recap2UI(ui: RecapUI, navigation: (String) -> Unit = {}) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        TitleUI(navigation)
        WeightUI(navigation, ui)
        BodyDataUI(ui, navigation)
        MeasurementsUI(ui, navigation)
        BottomUI(navigation)
    }
}

@Composable
private fun BottomUI(navigation: (String) -> Unit) {
    Button(
        onClick = { navigation(Navigation.NEW_MEASUREMENT.asString) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {

        Image(painter = painterResource(id = R.drawable.ic_new), contentDescription = "")
        Text(
            text = stringResource(id = R.string.new_measurements_title).toUpperCase(Locale.ROOT),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
private fun MeasurementsUI(
    ui: RecapUI,
    navigation: (String) -> Unit
) {
    Text(
        text = stringResource(id = R.string.measurements_title),
        modifier = Modifier.padding(
            top = 24.dp,
            start = 16.dp,
            bottom = 16.dp
        ),
        fontSize = 24.sp,
        fontWeight = FontWeight.Light
    )

    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        MeasurementElement(
            title = stringResource(id = R.string.hip),
            start = ui.hipStart,
            end = ui.hipEnd,
            delta = ui.hipDelta,
            modifier = Modifier.padding(start = 8.dp, end = 4.dp)
        ) {
            navigation("${Navigation.CHART.asString}${Charts.HIP.type}")
        }

        MeasurementElement(
            title = stringResource(id = R.string.umbilical),
            start = ui.umbilicalStart,
            end = ui.umbilicalEnd,
            delta = ui.umbilicalDelta,
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            navigation("${Navigation.CHART.asString}${Charts.UMBILICAL.type}")
        }

        MeasurementElement(
            title = stringResource(id = R.string.waist),
            start = ui.waistStart,
            end = ui.waistEnd,
            delta = ui.waistDelta,
            modifier = Modifier.padding(start = 4.dp, end = 16.dp)
        ) {
            navigation("${Navigation.CHART.asString}${Charts.WAIST.type}")
        }
    }
}

@Composable
private fun MeasurementElement(
    title: String,
    start: Float,
    end: Float,
    delta: Float,
    modifier: Modifier = Modifier,
    navigation: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(all = 4.dp)
            .size(width = 240.dp, height = 180.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .clickable { navigation() }
                .padding(all = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = ""
                )
                Text(text = "$start")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_last_check),
                    contentDescription = ""
                )
                Text(text = "$end")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_progress),
                    contentDescription = "",
                    modifier = Modifier.size(width = 56.dp, height = 56.dp)
                )
                Text(
                    text = String.format("%.2f", delta),
                    fontSize = 36.sp
                )
            }
        }
    }
}

@Composable
private fun BodyDataUI(
    ui: RecapUI,
    navigation: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column(modifier = Modifier.padding(all = 8.dp)) {
            Text(
                text = stringResource(id = R.string.body_data_title),
                modifier = Modifier.padding(8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
            ) {
                Column(modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { navigation("${Navigation.CHART.asString}${Charts.BMI.type}") }
                ) {
                    Text(
                        text = stringResource(id = R.string.bmi),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "${ui.bmiEnd} (${ui.bmiDelta})",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontWeight = FontWeight.Light
                    )
                }

                Column(modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { navigation("${Navigation.CHART.asString}${Charts.BODY_FAT.type}") }
                ) {
                    Text(
                        text = stringResource(id = R.string.body_fat),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "${ui.bodyFatPctEnd} (${ui.bodyFatPctDelta})",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontWeight = FontWeight.Light
                    )
                }
            }

        }
    }
}

@Composable
private fun TitleUI(navigation: (String) -> Unit) {
    user()?.let { user ->
        Box(contentAlignment = Alignment.CenterEnd) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp, top = 16.dp, bottom = 16.dp
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Hello,",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = user.firstName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            CoilImage(
                data = user.photoUrl ?: PLACEHOLDER_URL,
                contentDescription = "User profile",
                modifier = Modifier
                    .padding(all = 16.dp)
                    .requiredSize(width = 48.dp, height = 48.dp)
                    .clickable { navigation(Navigation.SETTINGS.asString) },
                requestBuilder = {
                    transformations(CircleCropTransformation())
                }
            )
        }
    }
}

@Composable
private fun WeightUI(navigation: (String) -> Unit, ui: RecapUI) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(all = 8.dp)
                .shadow(elevation = 8.dp, shape = CircleShape)
                .clip(CircleShape)
                .clickable(role = Role.Button) {
                    navigation("${Navigation.CHART.asString}${Charts.WEIGHT.type}")
                },
            shape = CircleShape,
            border = BorderStroke(width = 2.dp, color = Color.Green)
        ) {
            Column(
                modifier = Modifier.padding(all = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${ui.weightEnd}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp
                )
                Text(
                    text = "KG",
                    fontSize = 24.sp
                )
            }
        }
    }
}

private val previewData = RecapUI(
    weightStart = 95f,
    bmiStart = 31f,
    waistStart = 120f,
    umbilicalStart = 115f,
    hipStart = 130f,
    bodyFatPctStart = 30f,
    weightEnd = 85f,
    bmiEnd = 28f,
    waistEnd = 140f,
    umbilicalEnd = 90f,
    hipEnd = 110f,
    bodyFatPctEnd = 28f,
    weightDelta = -10f,
    bmiDelta = -3f,
    waistDelta = 20f,
    umbilicalDelta = -25f,
    hipDelta = -20f,
    bodyFatPctDelta = -2f
)

@Preview(
    name = "Day",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun Progress2UI_Day() {
    DietTheme(darkTheme = false) {
        Recap2UI(ui = previewData)
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
fun Progress2UI_Night() {
    DietTheme(darkTheme = true) {
        Recap2UI(ui = previewData)
    }
}