package io.rob.diet.charts

import android.content.res.Configuration
import android.view.MotionEvent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.rob.diet.Charts
import io.rob.diet.R
import io.rob.diet.common.Lce
import io.rob.diet.compose.ComposeViewModel
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

    val lastTap: MutableState<Float?> = remember { mutableStateOf(null) }
    val lastSelectedIndex = remember { mutableStateOf(-1) }

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h1
        )

        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastTap.value = it.x
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        lastTap.value = null
                        lastSelectedIndex.value = -1
                        true
                    }
                    else -> false
                }
            },
            onDraw = {

                val width = size.width
                val height = size.height

                val verticalFactor = height / points.maxOrNull()!!
                val horizontalFactor = width / points.size

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

                lastTap.value?.let { x ->

                    val column = x / horizontalFactor
                    val lowerBound = truncate(column).toInt()
                    val upperBound = column.roundToInt()

                    /**
                     * Calculate index based on the closest bound.
                     * if the space between the touch point and the upper bound is smaller than
                     * the space between the touch point and the lower bound AND the upper bound does
                     * not exceed the number of items, than it will choose the upper bound, otherwise
                     * it will choose the lower one
                     */
                    val index =
                        if ((upperBound.toFloat() - column) < (column - lowerBound)
                            && upperBound < points.size
                        ) {
                            upperBound
                        } else {
                            lowerBound
                        }

                    lastSelectedIndex.value = index

                    /**
                     * Draw a rectangle column to highlight the value
                     */

                    val startingX = (index * horizontalFactor) - (horizontalFactor / 2)

                    val topLeft = Offset(x = startingX, y = 0f)
                    val size = Size(width = horizontalFactor, height = height)

                    drawRect(
                        color = pointColor,
                        topLeft = topLeft,
                        size = size,
                        alpha = .5f
                    )
                }
            })

        Text(
            text = stringResource(id = R.string.history_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h1
        )

        points.forEachIndexed { index, value ->

            var backModifier = Modifier
                .fillMaxWidth()
            var textColor = MaterialTheme.typography.body1.color

            if (index == lastSelectedIndex.value) {
                backModifier = backModifier.background(MaterialTheme.colors.primary)
                textColor = MaterialTheme.colors.background
            }

            Box(modifier = backModifier) {
                Text(
                    text = "${descriptions[index]}: $value",
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                    color = textColor
                )
            }

        }
    }
}

@Composable
fun ChartsUI(type: Charts, viewModel: ComposeViewModel = viewModel()) {
    val state by viewModel.chartData.observeAsState(initial = Lce.Loading)

    Crossfade(targetState = state) {
        when (it) {
            is Lce.Loading -> LoadingUI()
            is Lce.Success -> LineChart(
                title = it.data.title,
                points = it.data.points,
                descriptions = it.data.descriptions
            )
            else -> Box {}
        }
    }

    if (state !is Lce.Success) {
        viewModel.fetchPointsFor(type)
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
    val points = arrayOf(3f, 2f, 8f, 5f, 10f, 11f, 1f, 0f, 3f, 2f)
    val descriptions = arrayOf(
        "Mon, May 1st 2020",
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