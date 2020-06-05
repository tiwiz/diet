package io.rob.diet.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.rob.diet.storage.DietDao
import io.rob.diet.storage.DietDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDietDatabase(context: Application): DietDatabase =
        DietDatabase.buildDataBase(context)

    @Provides
    @Singleton
    fun provideDietDao(db: DietDatabase): DietDao = db.dao()
}