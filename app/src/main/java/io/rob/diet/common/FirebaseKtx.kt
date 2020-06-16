package io.rob.diet.common

import com.google.firebase.auth.FirebaseAuth
import io.rob.diet.settings.User

fun FirebaseAuth.user() : User? =
    FirebaseAuth.getInstance().currentUser?.let {
        User(displayName = it.displayName, photoUrl = it.photoUrl, id = it.uid)
    }

fun FirebaseAuth.userOrEmpty() = user() ?: User.empty