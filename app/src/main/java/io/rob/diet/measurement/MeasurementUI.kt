package io.rob.diet.measurement

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.rob.diet.R
import io.rob.diet.compose.DietTitle
import io.rob.diet.progress.UiMeasurement
import io.rob.diet.ui.theme.DietTheme
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun LabeledInput(
    label: String,
    modifier: Modifier = Modifier,
    updater: (String) -> Unit = {},
    focusRequester: FocusRequester,
    isLast: Boolean = false
) {
    var text by rememberSaveable { mutableStateOf("") }
    var isInvalid by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    fun updateOrError(doNext: () -> Unit) {
        if (text.toFloatOrNull() == null) {
            isInvalid = true
        } else {
            isInvalid = false
            updater(text)
            doNext()
        }
    }

    OutlinedTextField(value = text,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .onFocusChanged {
                isInvalid = text.toFloatOrNull() == null
            },
        onValueChange = { text = it },
        label = { Text(text = label) },
        isError = isInvalid,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number,
            imeAction = if (isLast) {
                ImeAction.Send
            } else {
                ImeAction.Next
            }
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                updateOrError {
                    keyboardController?.hide()
                }
            },
            onNext = {
                updateOrError {
                    focusRequester.requestFocus()
                }
            }
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MeasurementUI(completion: (UiMeasurement) -> Unit = {}) {

    val focusRequesterBmi = remember { FocusRequester() }
    val focusRequesterBodyFat = remember { FocusRequester() }
    val focusRequesterWaist = remember { FocusRequester() }
    val focusRequesterUmbilical = remember { FocusRequester() }
    val focusRequesterHip = remember { FocusRequester() }

    val measurement = remember { UiMeasurement() }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {

        DietTitle(titleRes = R.string.new_measurements_title)
        LabeledInput(
            label = stringResource(id = R.string.weight_hint),
            focusRequester = focusRequesterBmi,
            updater = { weight -> measurement.weight = weight.toFloat() }
        )

        LabeledInput(
            label = stringResource(id = R.string.bmi),
            modifier = Modifier.focusRequester(focusRequesterBmi),
            focusRequester = focusRequesterBodyFat,
            updater = { bmi -> measurement.bmi = bmi.toFloat() }
        )

        LabeledInput(
            label = stringResource(id = R.string.body_fat),
            modifier = Modifier.focusRequester(focusRequesterBodyFat),
            focusRequester = focusRequesterWaist
        )

        LabeledInput(
            label = stringResource(id = R.string.waist),
            modifier = Modifier.focusRequester(focusRequesterWaist),
            focusRequester = focusRequesterUmbilical,
            updater = { waist -> measurement.waist = waist.toFloat() }
        )

        LabeledInput(
            label = stringResource(id = R.string.umbilical),
            modifier = Modifier.focusRequester(focusRequesterUmbilical),
            focusRequester = focusRequesterHip,
            updater = { umbilical -> measurement.umbilical = umbilical.toFloat() }
        )

        LabeledInput(
            label = stringResource(id = R.string.hip),
            modifier = Modifier.focusRequester(focusRequesterHip),
            focusRequester = focusRequesterHip,
            isLast = true,
            updater = { hip -> measurement.hip = hip.toFloat() }
        )

        Button(
            onClick = { completion(measurement) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.save_new_food).toUpperCase(Locale.getDefault()))
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
    DietTheme(darkTheme = false) {
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
    DietTheme(darkTheme = true) {
        MeasurementUI()
    }
}