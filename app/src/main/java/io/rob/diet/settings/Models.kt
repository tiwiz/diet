package io.rob.diet.settings

import android.net.Uri

data class User(
    val displayName: String? = null,
    val photoUrl: Uri? = null,
    val id: String? = null
) {
    val firstName = displayName?.split(" ")?.firstOrNull() ?: "User"

    fun isEmpty() : Boolean = this == empty

    companion object {
        val empty = User()
    }
}