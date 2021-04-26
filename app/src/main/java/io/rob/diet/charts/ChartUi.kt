package io.rob.diet.charts

import android.content.res.Configuration
import android.view.MotionEvent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.rob.diet.Charts
import io.rob.diet.R
import io.rob.diet.common.Lce
import io.rob.diet.compose.ComposeViewModel
import io.rob.diet.compose.DietTitle
import io.rob.diet.compose.ErrorUI
import io.rob.diet.compose.LoadingUI
import io.rob.diet.ui.theme.DietTheme
import kotlin.math.roundToInt
import kotlin.math.truncate

@Composable
fun LineChart(
    title: String,
    points: Array<Float>,
    descriptions: Array<String>
) {
    val pointColor = MaterialTheme.colors.primaryVariant
    val lineColor = MaterialTheme.colors.primary
    val systemUiController = rememberSystemUiController()

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        systemUiController.setStatusBarColor(
            color = MaterialTheme.colors.secondaryVariant,
            darkIcons = MaterialTheme.colors.isLight
        )
        Card(
            backgroundColor = MaterialTheme.colors.secondaryVariant,
            shape = RoundedCornerShape(
                bottomEnd = 24.dp,
                bottomStart = 24.dp
            ),
            elevation = 0.dp
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 32.dp
                )
            ) {
                DietTitle(
                    title = title,
                    fontWeight = FontWeight.Bold
                )
                Chart(
                    points = points,
                    pointColor = pointColor,
                    lineColor = lineColor
                )
            }
        }

        DietTitle(titleRes = R.string.history_title)

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            points.ChartRow { left, right ->
                HistoryElement(
                    index = left.index,
                    descriptions = descriptions,
                    value = left.value,
                    modifier = Modifier.weight(1f)
                )

                HistoryElement(
                    index = right.index,
                    descriptions = descriptions,
                    value = right.value,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun HistoryElement(
    index: Int,
    descriptions: Array<String>,
    value: Float,
    modifier: Modifier
) {
    val textColor = MaterialTheme.typography.body1.color

    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = descriptions[index],
                fontSize = 10.sp,
                color = textColor
            )
            Text(
                text = "$value",
                fontSize = 18.sp,
                color = textColor
            )
        }
    }
}

@Composable
private fun Chart(
    points: Array<Float>,
    pointColor: Color,
    lineColor: Color
) {

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(size = 8.dp),
        modifier = Modifier.padding(all = 4.dp)
    ) {
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
            onDraw = {

                val width = size.width
                val height = size.height

                val verticalFactor = height / points.maxOrNull()!!
                val horizontalFactor = width / (points.size - 1)

                points.forEachIndexed { hStep, value ->
                    drawCircle(
                        color = pointColor,
                        radius = 10f,
                        center = Offset(
                            x = hStep * horizontalFactor,
                            y = height - (value * verticalFactor)
                        )
                    )

                    if (hStep > 0) {
                        val startingOffset = Offset(
                            x = (hStep - 1) * horizontalFactor,
                            y = height - (points[hStep - 1] * verticalFactor)
                        )

                        val endingOffset = Offset(
                            x = hStep * horizontalFactor,
                            y = height - (value * verticalFactor)
                        )
                        drawLine(
                            color = lineColor,
                            start = startingOffset,
                            end = endingOffset,
                            strokeWidth = 5f
                        )
                    }
                }
            })
    }
}

@Composable
fun ChartsUI(type: Charts, viewModel: ComposeViewModel = viewModel()) {
    val state by viewModel.chartData.observeAsState(initial = Lce.Loading)

    fun fetchData() = viewModel.fetchPointsFor(type)

    Crossfade(targetState = state) {
        when (it) {
            is Lce.Loading -> LoadingUI()
            is Lce.Success -> LineChart(
                title = it.data.title,
                points = it.data.points,
                descriptions = it.data.descriptions
            )
            else -> ErrorUI { fetchData() }
        }
    }

    if (state !is Lce.Success) {
        fetchData()
    }
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
    val points = arrayOf(93.4f, 91.2f, 90.2f, 89.4f, 89.1f, 86.9f, 85.7f, 85.2f, 86.2f)
    val descriptions = arrayOf(
        "Mon, May 1st 2020",
        "Tue, June 2nd 2020",
        "Wed, July 3rd 2020",
        "Thu, August 4th 2020",
        "Fri, September 5th 2020",
        "Sat, October 6th 2020",
        "Sun, November 7th 2020",
        "Mon, December 8th 2020",
        "Tue, January 9th 2020"
    )
    DietTheme(darkTheme = false) {
        LineChart("Line by day", points, descriptions)
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
    val points = arrayOf(5f, 4f, 3f, 2f, 1f, 0f, 3f, 6f, 9f)
    val descriptions = arrayOf(
        "Tue, June 2nd 2020",
        "Wed, July 3rd 2020",
        "Thu, August 4th 2020",
        "Fri, September 5th 2020",
        "Sat, October 6th 2020",
        "Sun, November 7th 2020",
        "Mon, December 8th 2020",
        "Tue, January 9th 2020",
        "Wed, February 10th 2020"
    )
    DietTheme(darkTheme = true) {
        LineChart("Line by night", points, descriptions)
    }
}