package io.rob.diet.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.rob.diet.storage.*

@Module
@InstallIn(ActivityRetainedComponent::class)
object DatabaseModule {

    @Provides
    @ActivityRetainedScoped
    fun provideDietDatabase(context: Application): DietDatabase =
        DietDatabase.buildDataBase(context)

    @Provides
    @ActivityRetainedScoped
    fun provideDietDao(db: DietDatabase): DietDao = db.dietDao()

    @Provides
    @ActivityRetainedScoped
    fun provideProgressDao(db: DietDatabase) : ProgressDao = db.progressDao()

    @Provides
    @ActivityRetainedScoped
    fun provideMeasurementRepository(localRepo: LocalRepository) : ProgressRepository = localRepo
}