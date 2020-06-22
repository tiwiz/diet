package io.rob.diet.common

import com.google.firebase.auth.FirebaseAuth
import io.rob.diet.settings.User
import javax.inject.Inject


class AuthenticationManager @Inject constructor(){

    private var user : User? = user()

    init {
        FirebaseAuth.getInstance().addAuthStateListener {
            user = it.currentUser?.let { firebaseUser ->
                User(
                    displayName = firebaseUser.displayName,
                    photoUrl = firebaseUser.photoUrl,
                    id = firebaseUser.uid
                )
            }
        }
    }

    fun isUserAuthenticated() : Boolean = user != null
}