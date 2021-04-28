package io.rob.diet.settings

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.rob.diet.R
import io.rob.diet.compose.AppTitle
import io.rob.diet.ui.theme.DietTheme

@Composable
fun LoginUI(
    systemUiController: SystemUiController,
    navigation: () -> Unit = {}
) {

    val loginContent = rememberLauncherForActivityResult(LogInContract()) {
        navigation()
    }

    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.primary,
        darkIcons = false
    )

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.primary)
            .fillMaxSize()
            .padding(top = 72.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_app_logo),
            contentDescription = "",
            modifier = Modifier.requiredSize(size = 300.dp)
        )

        AppTitle(color = MaterialTheme.colors.background)

        Button(
            onClick = {
                loginContent.launch(null)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = ""
            )
            Text(
                text = stringResource(id = R.string.sign_in_with_google),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
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
        LoginUI(systemUiController = rememberSystemUiController())
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
        LoginUI(systemUiController = rememberSystemUiController())
    }
}