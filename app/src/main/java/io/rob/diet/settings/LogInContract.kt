package io.rob.diet.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import io.rob.diet.R
import io.rob.diet.common.userOrEmpty
import timber.log.Timber

class LogInContract : ActivityResultContract<Nothing, User>() {

    private val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    override fun parseResult(resultCode: Int, intent: Intent?): User {
        val response = IdpResponse.fromResultIntent(intent)

        return if (resultCode == Activity.RESULT_OK) {
            userOrEmpty()
        } else {
            Timber.e("Error during authentication: ${response?.error?.message}")
            User.empty
        }
    }

    override fun createIntent(context: Context, input: Nothing?): Intent =
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.Theme_MaterialComponents_DayNight_NoActionBar)
            .setIsSmartLockEnabled(true)
            .build()
}