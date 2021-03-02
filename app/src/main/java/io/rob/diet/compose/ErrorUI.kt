package io.rob.diet.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import io.rob.diet.R
import io.rob.diet.ui.theme.DietTheme

@Composable
fun ErrorUI(retry: (() -> Unit)? = null) {
    val animationSpec = remember {
        LottieAnimationSpec.Asset("error.json")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            animationSpec,
            modifier = Modifier.requiredSize(196.dp)
        )
        retry?.let { retryCallback ->
            DietButton(label = stringResource(id = R.string.retry), borderColor = Color.Red) {
                retryCallback()
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
fun ErrorUI_Day() {
    DietTheme(darkTheme = false) {
        ErrorUI {}
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
fun ErrorUI_Night() {
    DietTheme(darkTheme = true) {
        ErrorUI {}
    }
}