package com.sameep.watertracker.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.sameep.watertracker.data.preferencedatastore.PreferenceDataStore
import com.sameep.watertracker.data.roomdatabase.WaterDatabaseDao
import com.sameep.watertracker.data.roomdatabase.WaterDatabase
import com.sameep.watertracker.repository.WaterDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideWaterDatabase(@ApplicationContext context:Context): WaterDatabase
        = Room.databaseBuilder(
            context,
            WaterDatabase::class.java,
            "water_db"
        ).fallbackToDestructiveMigrationOnDowngrade().build()

    @Singleton
    @Provides
    fun provideWaterDatabaseDao(waterDatabase: WaterDatabase): WaterDatabaseDao
        = waterDatabase.waterDatabaseDao()

    @Singleton
    @Provides
    fun provideWaterDataRepository(waterDatabaseDao: WaterDatabaseDao): WaterDataRepository
        = WaterDataRepository(waterDatabaseDao = waterDatabaseDao)

    @Singleton
    @Provides
    fun providePreferenceDataStore(@ApplicationContext context:Context): PreferenceDataStore
        = PreferenceDataStore(context)
}