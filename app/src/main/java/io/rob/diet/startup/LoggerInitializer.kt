package io.rob.diet.startup

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber

class LoggerInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Timber.plant(Timber.DebugTree())
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = arrayListOf()
}