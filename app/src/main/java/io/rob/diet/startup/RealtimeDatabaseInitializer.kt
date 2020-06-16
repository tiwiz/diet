package io.rob.diet.startup

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RealtimeDatabaseInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Firebase.database.setPersistenceEnabled(true)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = arrayListOf()
}