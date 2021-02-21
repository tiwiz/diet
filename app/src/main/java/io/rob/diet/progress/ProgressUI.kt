package io.rob.diet.progress

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.rob.diet.R
import io.rob.diet.charts.LineChart
import io.rob.diet.ui.theme.DietTheme

@Composable
fun ElementUI(
    title: String,
    startingValue: Float,
    endingValue: Float,
    delta: Float,
    clickBehaviour: () -> Unit = {}
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
        .clickable {
            clickBehaviour()
        }) {
        Box(modifier = Modifier
            .padding(top = 18.dp)
            .fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(width = 1.dp, color = Color.LightGray),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(top = 20.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        imageVector = vectorResource(id = R.drawable.ic_history),
                        contentDescription = ""
                    )
                    Text(text = "$startingValue")
                    Image(
                        imageVector = vectorResource(id = R.drawable.ic_arrow_right),
                        contentDescription = "",
                        modifier = Modifier.sizeIn(minWidth = 96.dp)
                    )
                    Text(text = "$endingValue")
                    Image(
                        imageVector = vectorResource(id = R.drawable.ic_last_check),
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
                        imageVector = vectorResource(id = R.drawable.ic_progress),
                        contentDescription = "",
                        modifier = Modifier.size(width = 64.dp, height = 64.dp)
                    )
                    Text(
                        text = "$delta",
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
                title, modifier = Modifier
                    .background(MaterialTheme.colors.background),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h2,

            )
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
fun ProgressUI_Day() {
    DietTheme(darkTheme = false) {
        ElementUI(title = "Test Day", startingValue = 25f, endingValue = 100f, delta = 5f)
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
        ElementUI(title = "Test Day", startingValue = 25f, endingValue = 100f, delta = 5f)
    }
}