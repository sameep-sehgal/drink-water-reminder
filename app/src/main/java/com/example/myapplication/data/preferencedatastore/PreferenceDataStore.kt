package com.example.myapplication.data.preferencedatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_storage")

class PreferenceDataStore @Inject constructor(@ApplicationContext context:Context)
    : PreferenceDataStoreInterface{

    private val dataStore = context.dataStore

    //preference keys
    private object PreferencesKeys {
        val RECOMMENDED_WATER_INTAKE = intPreferencesKey("recommended_water_intake")
    }

    override fun recommendedWaterIntake() =
        dataStore.data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[PreferencesKeys.RECOMMENDED_WATER_INTAKE] ?: 2500
        }

    override suspend fun setRecommendedWaterIntake(amount: Int) {
        dataStore.edit {
            it[PreferencesKeys.RECOMMENDED_WATER_INTAKE] = amount
        }
    }

    override fun gender(): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun setGender(gender: String) {
        TODO("Not yet implemented")
    }

    override fun weight(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun setWeight(amount: Int) {
        TODO("Not yet implemented")
    }
}