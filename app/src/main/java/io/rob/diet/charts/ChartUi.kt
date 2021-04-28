package io.rob.diet.charts

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import io.rob.diet.Charts
import io.rob.diet.R
import io.rob.diet.common.Lce
import io.rob.diet.compose.ComposeViewModel
import io.rob.diet.compose.DietTitle
import io.rob.diet.compose.ErrorUI
import io.rob.diet.compose.LoadingUI
import io.rob.diet.ui.theme.DietTheme
import kotlin.math.roundToInt

@Composable
fun LineChart(
    title: String,
    points: Array<Float>,
    descriptions: Array<String>
) {
    val pointColor = MaterialTheme.colors.primary

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Card(
            backgroundColor = MaterialTheme.colors.primary,
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

                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    color = MaterialTheme.colors.background,
                    style = MaterialTheme.typography.h1,
                    fontWeight = FontWeight.Bold
                )

                Chart(
                    points = points,
                    pointColor = pointColor
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
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                        .weight(1f)
                )

                if (right != null) {
                    HistoryElement(
                        index = right.index,
                        descriptions = descriptions,
                        value = right.value,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                            .weight(1f)
                    )
                } else {
                    Box(modifier = Modifier.weight(1f))
                }
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
        modifier = modifier,
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
    pointColor: Color
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

                val min = points.minOrNull()!!
                val max = points.maxOrNull()!!
                val delta = max - min
                val verticalFactor = height / delta
                val horizontalFactor = width / (points.size - 1)

                (min.roundToInt()..max.roundToInt()).forEach { y ->
                    val realY = height - ((y - min) * verticalFactor)
                    val start = Offset(x = 0f, y = realY)
                    val end = Offset(x = width, y = realY)
                    drawLine(
                        color = Color.DarkGray,
                        start = start,
                        end = end,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                }

                points.mapIndexed { hStep, value ->
                    Offset(
                        x = hStep * horizontalFactor,
                        y = height - ((value - min) * verticalFactor)
                    )
                }.toList().let { points ->
                    drawPoints(
                        points = points,
                        pointMode = PointMode.Polygon,
                        color = pointColor,
                        strokeWidth = 8f,
                        cap = StrokeCap.Round
                    )
                }
            })
    }
}

@Composable
fun ChartsUI(
    type: Charts,
    systemUiController: SystemUiController,
    viewModel: ComposeViewModel = viewModel()
) {
    val state by viewModel.chartData.observeAsState(initial = Lce.Loading)

    fun fetchData() = viewModel.fetchPointsFor(type)

    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.primary,
        darkIcons = false
    )

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