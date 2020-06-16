package io.rob.diet.startup

import android.content.Context
import androidx.startup.Initializer
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import io.rob.diet.BuildConfig

class FlipperInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)
            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            client.start()
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> =
        arrayListOf(SoLoaderInitializer::class.java)
}