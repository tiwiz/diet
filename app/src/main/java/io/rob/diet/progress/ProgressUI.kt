package io.rob.diet.progress

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import io.rob.diet.charts.LineChart
import io.rob.diet.ui.theme.DietTheme

@Composable
fun ElementUI(
    title: String,
    startingValue: Float,
    endingValue: Float,
    clickBehaviour: () -> Unit = {}
) {

}

@Preview(
    name = "Day",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun LineChartPreview_Day() {
    DietTheme(darkTheme = false) {
        ElementUI(title = "Test Day", startingValue = 25f, endingValue = 100f)
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
fun LineChartPreview_Night() {
    DietTheme(darkTheme = true) {
        ElementUI(title = "Test Day", startingValue = 25f, endingValue = 100f)
    }
}