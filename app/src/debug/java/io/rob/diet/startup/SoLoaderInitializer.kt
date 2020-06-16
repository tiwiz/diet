package io.rob.diet.startup

import android.content.Context
import androidx.startup.Initializer
import com.facebook.soloader.SoLoader

class SoLoaderInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        SoLoader.init(context, false)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = arrayListOf()
}