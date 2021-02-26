package io.rob.diet.settings

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.rob.diet.ui.theme.DietTheme

@Composable
fun AnonymousUserUI(onClick: () -> Unit = {}) {


    Text(
        text = "Login".toUpperCase(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .border(
                border = BorderStroke(width = 1.dp, color = Color.LightGray),
                shape = RoundedCornerShape(2.dp)
            )
            .clickable { onClick() },
        style = MaterialTheme.typography.body2
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
fun AnonymousUserUI_Day() {
    DietTheme(darkTheme = false) {
        AnonymousUserUI()
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
fun AnonymousUserUI_Night() {
    DietTheme(darkTheme = true) {
        AnonymousUserUI()
    }
}