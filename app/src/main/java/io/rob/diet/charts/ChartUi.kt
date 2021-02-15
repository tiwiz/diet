package io.rob.diet.charts

import android.content.res.Configuration
import android.view.MotionEvent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.rob.diet.ui.theme.DietTheme
import kotlin.math.roundToInt
import kotlin.math.truncate


@Composable
fun LineChart(
    title: String,
    points: Array<Float>
) {
    val pointColor = MaterialTheme.colors.primaryVariant
    val lineColor = MaterialTheme.colors.primary

    val lastTap : MutableState<Float?> = mutableStateOf(null)
    val lastSelectedIndex = mutableStateOf(-1)

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
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraLight,
            color = MaterialTheme.colors.primary,
            fontSize = 24f.sp
        )

        Canvas(modifier = Modifier
            .fillMaxWidth()
            .preferredHeight(200.dp)
            .padding(16.dp)
            .pointerInteropFilter {
                if (it.action == MotionEvent.ACTION_DOWN) {
                    lastTap.value = it.x
                    true
                } else {
                    false
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
                            && upperBound < points.size) {
                            upperBound
                        } else {
                            lowerBound
                        }

                    lastSelectedIndex.value = index
                }
            })

        Text(text = "Storico")

        points.forEachIndexed { index, value ->
            val modifier = Modifier

            if (index == lastSelectedIndex.value) {
                modifier.background(Color.Red)
            }
            Text(text = "Value at index $index is $value", modifier = modifier)
        }
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
    DietTheme(darkTheme = false) {
        LineChart("Line by day", points)
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
    DietTheme(darkTheme = true) {
        LineChart("Line by night", points)
    }
}