package io.rob.diet

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class DietApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
    }
}