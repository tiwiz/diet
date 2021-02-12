package io.rob.diet.measurement

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.rob.diet.ui.theme.DietTheme

@Composable
fun LabeledInput(label: String) {
    val text = remember { "" }
    OutlinedTextField(
        value = text,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        onValueChange = { text },
        label = { Text(text = label) }
    )
}

@Preview(
    name = "Day",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun Preview_Day() {
    DietTheme {
        LabeledInput("Text")
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
fun Preview_Night() {
    DietTheme {
        LabeledInput("Text")
    }
}