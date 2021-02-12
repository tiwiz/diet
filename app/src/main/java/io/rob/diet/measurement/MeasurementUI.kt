package io.rob.diet.measurement

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.rob.diet.R
import io.rob.diet.ui.theme.DietTheme
import java.util.*

@Composable
fun LabeledInput(
    label: String,
    updater: (String) -> Unit = {},
    isLast: Boolean = false
) {
    val text = remember { mutableStateOf("") }
    OutlinedTextField(
        value = text.value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onValueChange = {
            text.value = it
            updater(it)
        },
        label = { Text(label) },
        onImeActionPerformed = { imeAction, _ ->
            if (imeAction == ImeAction.Next) {
                println("Next")
            } else {
                println("Send")
            }
        },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number,
            imeAction = if (isLast) {
                ImeAction.Send
            } else {
                ImeAction.Next
            }
        )
    )
}

@Composable
fun MeasurementUI() {
    LazyColumn {
        item {
            Text(
                text = stringResource(id = R.string.new_measurements_title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraLight
            )
        }
        item {
            LabeledInput(label = stringResource(id = R.string.weight_hint))
        }
        item {
            LabeledInput(label = stringResource(id = R.string.bmi))
        }
        item {
            LabeledInput(label = stringResource(id = R.string.body_fat))
        }
        item {
            LabeledInput(label = stringResource(id = R.string.waist))
        }
        item {
            LabeledInput(label = stringResource(id = R.string.umbilical))
        }
        item {
            LabeledInput(
                label = stringResource(id = R.string.hip),
                isLast = true
            )
        }
        item {
            Button(
                onClick = { println("Click") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(id = R.string.save_new_food).toUpperCase(Locale.getDefault()))
            }
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
fun Preview_Day() {
    DietTheme {
        MeasurementUI()
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
        MeasurementUI()
    }
}