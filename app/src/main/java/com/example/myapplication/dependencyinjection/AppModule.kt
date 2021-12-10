package com.example.myapplication.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.preferencedatastore.PreferenceDataStore
import com.example.myapplication.data.roomdatabase.WaterDatabaseDao
import com.example.myapplication.data.roomdatabase.WaterDatabase
import com.example.myapplication.repository.WaterDataRepository
import com.example.myapplication.ui.screens.hometab.HomeTabViewModel
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
    fun provideWaterDatabase(@ApplicationContext context:Context):WaterDatabase
        = Room.databaseBuilder(
            context,
            WaterDatabase::class.java,
            "water_db"
        ).fallbackToDestructiveMigrationOnDowngrade().build()

    @Singleton
    @Provides
    fun provideWaterDatabaseDao(waterDatabase: WaterDatabase):WaterDatabaseDao
        = waterDatabase.waterDatabaseDao()

    @Singleton
    @Provides
    fun provideWaterDataRepository(waterDatabaseDao: WaterDatabaseDao,preferenceDataStore: PreferenceDataStore):WaterDataRepository
        = WaterDataRepository(waterDatabaseDao = waterDatabaseDao, preferenceDataStore = preferenceDataStore)

    //Not reqd viewmodels are already annotated by HiltViewModel
//    @Singleton
//    @Provides
//    fun provideHomeTabViewModel(waterDataRepository: WaterDataRepository):HomeTabViewModel
//        = HomeTabViewModel(waterDataRepository = waterDataRepository)

    @Singleton
    @Provides
    fun providePreferenceDataStore(@ApplicationContext context:Context):PreferenceDataStore
        = PreferenceDataStore(context)
}