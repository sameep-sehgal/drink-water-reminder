package com.example.myapplication.data.preferencedatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
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
      //Personal Settings
      val GENDER = stringPreferencesKey("gender")
      val WEIGHT = intPreferencesKey("weight")
      val UNITS = stringPreferencesKey("units")
      val RECOMMENDED_WATER_INTAKE = intPreferencesKey("recommended_water_intake")
      val DAILY_WATER_GOAL = intPreferencesKey("daily_water_goal")

      //Reminder Settings
      val REMINDER_PERIOD_START = stringPreferencesKey("reminder_period_start")
      val REMINDER_PERIOD_END = stringPreferencesKey("reminder_period_end")
      val REMINDER_GAP = intPreferencesKey("reminder_gap")
      val REMINDER_AFTER_GOAL_ACHIEVED = booleanPreferencesKey("reminder_after_goal_achieved")
      val REMINDER_SOUND = stringPreferencesKey("reminder_sound")
      
      //Main Settings
      val APP_THEME = stringPreferencesKey("app_theme")
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