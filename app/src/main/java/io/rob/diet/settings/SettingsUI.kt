package io.rob.diet.settings

import android.content.res.Configuration
import android.net.Uri
import android.service.autofill.UserData
import androidx.activity.compose.registerForActivityResult
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.transform.CircleCropTransformation
import dev.chrisbanes.accompanist.coil.CoilImage
import io.rob.diet.R
import io.rob.diet.common.Lce
import io.rob.diet.ui.theme.DietTheme
import java.util.*

const val PLACEHOLDER_URL = "https://i.ibb.co/cxtT1Pn/ic-launcher-playstore.png"

@Composable
fun AnonymousUserUI(onClick: () -> Unit = {}) {
    Column {
        Title()
        Button("Login") { onClick() }
    }
}

@Composable
private fun Button(label: String, onClick: () -> Unit = {}) {
    Text(
        text = label.toUpperCase(Locale.getDefault()),
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

@Composable
private fun Title() {
    Text(
        text = stringResource(id = R.string.settings_title),
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.h1
    )
}

@Composable
fun LoggedUserUI(user: User, onClick: () -> Unit = {}) {

    Column {
        Title()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                data = user.photoUrl ?: PLACEHOLDER_URL,
                contentDescription = "User profile",
                modifier = Modifier.requiredSize(width = 96.dp, height = 96.dp),
                requestBuilder = {
                    transformations(CircleCropTransformation())
                }
            )
            user.displayName?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    style = MaterialTheme.typography.body2
                )
            }
        }
        Button(label = "Logout") { onClick() }
    }
}

@Composable
fun SettingsUI() {
    val viewModel: SettingsViewModel = viewModel()
    val state by viewModel.user.observeAsState()

    val loginContent = registerForActivityResult(LogInContract()) {
        viewModel.loadUserData()
    }

    Crossfade(targetState = state) {
        when (it) {
            is Lce.Loading -> Box {}
            is Lce.Success -> if (it.data.isEmpty()) {
                AnonymousUserUI {
                    loginContent.launch(null)
                }
            } else {
                LoggedUserUI(it.data) {
                    viewModel.logOut()
                }
            }
            else -> Box {}
        }
    }

    if (state !is Lce.Success) {
        viewModel.loadUserData()
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

@Preview(
    name = "Day - Logged in",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun LoggedUserUI_Day() {
    val user = User(
        displayName = "Roberto Orgiu",
        photoUrl = Uri.parse(PLACEHOLDER_URL)
    )
    DietTheme(darkTheme = false) {
        LoggedUserUI(user)
    }
}

@Preview(
    name = "Night - Logged in",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoggedUserUI_Night() {
    val user = User(
        displayName = "Roberto Orgiu",
        photoUrl = Uri.parse(PLACEHOLDER_URL)
    )
    DietTheme(darkTheme = true) {
        LoggedUserUI(user)
    }
}