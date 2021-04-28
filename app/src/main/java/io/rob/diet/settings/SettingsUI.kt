package io.rob.diet.settings

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.transform.CircleCropTransformation
import dev.chrisbanes.accompanist.coil.CoilImage
import io.rob.diet.R
import io.rob.diet.common.Lce
import io.rob.diet.compose.*
import io.rob.diet.ui.theme.DietTheme

const val PLACEHOLDER_URL = "https://i.ibb.co/cxtT1Pn/ic-launcher-playstore.png"

@Composable
fun AnonymousUserUI(onClick: () -> Unit = {}) {
    Column {
        DietTitle(titleRes = R.string.settings_title)
        DietButton("Login") { onClick() }
        Legal()
    }
}


@Composable
private fun Legal() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        DietTitle(titleRes = R.string.licenses_title)
        DietSubtitle(label = stringResource(id = R.string.loading_animation))
        LinkifiedText(
            text = "Tanvi Sharma @ LottieFiles",
            link = "https://lottiefiles.com/22499-stay-healthy-eat-healty"
        )
        Divider(thickness = 2.dp, modifier = Modifier.padding(bottom = 8.dp, top = 8.dp))
        DietSubtitle(
            label = stringResource(id = R.string.error_animation),
            modifier = Modifier.padding(top = 24.dp)
        )
        LinkifiedText(
            text = "Thais Roese @ LottieFiles",
            link = "https://lottiefiles.com/38213-error"
        )
    }
}

@Composable
fun LoggedUserUI(user: User, onClick: () -> Unit = {}) {

    Column {
        DietTitle(titleRes = R.string.settings_title)
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
        DietButton(label = "Logout") { onClick() }
        Legal()
    }
}

@Composable
fun SettingsUI() {
    val viewModel: SettingsViewModel = viewModel()
    val state by viewModel.user.observeAsState()

    val loginContent = rememberLauncherForActivityResult(LogInContract()) {
        viewModel.loadUserData()
    }

    Crossfade(targetState = state) {
        when (it) {
            is Lce.Loading -> LoadingUI()
            is Lce.Success -> if (it.data.isEmpty()) {
                AnonymousUserUI {
                    loginContent.launch(null)
                }
            } else {
                LoggedUserUI(it.data) {
                    viewModel.logOut()
                }
            }
            else -> ErrorUI()
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