package com.example.myapplication.data.preferencedatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.utils.*
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
    val IS_USER_INFO_COLLECTED = booleanPreferencesKey("is_user_info_collected")

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

  override fun isUserInfoCollected(): Flow<Boolean> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.IS_USER_INFO_COLLECTED] ?: false
    }

  override suspend fun setIsUserInfoCollected(value: Boolean) {
    dataStore.edit {
      it[PreferencesKeys.IS_USER_INFO_COLLECTED] = value
    }
  }

  //Personal Settings

  override fun gender(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.GENDER] ?: Gender.NOT_SET
    }

  override suspend fun setGender(gender: String) {
    dataStore.edit {
      it[PreferencesKeys.GENDER] = gender
    }
  }

  override fun weight(): Flow<Int> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.WEIGHT] ?: Weight.NOT_SET
    }

  override suspend fun setWeight(weight: Int) {
    dataStore.edit {
      it[PreferencesKeys.WEIGHT] = weight
    }
  }

  override fun units(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.UNITS] ?: Units.KG_ML
    }

  override suspend fun setUnits(units: String) {
    dataStore.edit {
      it[PreferencesKeys.UNITS] = units
    }
  }

  override fun recommendedWaterIntake() =
    dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[PreferencesKeys.RECOMMENDED_WATER_INTAKE] ?: RecommendedWaterIntake.NOT_SET
    }

  override suspend fun setRecommendedWaterIntake(amount: Int) {
    dataStore.edit {
        it[PreferencesKeys.RECOMMENDED_WATER_INTAKE] = amount
    }
  }

  override fun dailyWaterGoal(): Flow<Int> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.DAILY_WATER_GOAL] ?: RecommendedWaterIntake.NOT_SET
    }

  override suspend fun setDailyWaterGoal(amount: Int) {
    dataStore.edit {
      it[PreferencesKeys.DAILY_WATER_GOAL] = amount
    }
  }

  //Reminder Settings

  override fun reminderPeriodStart(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.REMINDER_PERIOD_START] ?: ReminderPeriod.NOT_SET
    }

  override suspend fun setReminderPeriodStart(time: String) {
    dataStore.edit {
      it[PreferencesKeys.REMINDER_PERIOD_START] = time
    }
  }

  override fun reminderPeriodEnd(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.REMINDER_PERIOD_END] ?: ReminderPeriod.NOT_SET
    }

  override suspend fun setReminderPeriodEnd(time: String) {
    dataStore.edit {
      it[PreferencesKeys.REMINDER_PERIOD_END] = time
    }
  }

  override fun reminderGap(): Flow<Int> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.REMINDER_GAP] ?: ReminderGap.NOT_SET
    }

  override suspend fun setReminderGap(gapTime: Int) {
    dataStore.edit {
      it[PreferencesKeys.REMINDER_GAP] = gapTime
    }
  }

  override fun reminderAfterGoalAchieved(): Flow<Boolean> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.REMINDER_AFTER_GOAL_ACHIEVED] ?: false
    }

  override suspend fun setReminderAfterGoalAchieved(value: Boolean) {
    dataStore.edit {
      it[PreferencesKeys.REMINDER_AFTER_GOAL_ACHIEVED] = value
    }
  }

  override fun reminderSound(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.REMINDER_SOUND] ?: ReminderSound.POURING_WATER
    }

  override suspend fun setReminderSound(sound: String) {
    dataStore.edit {
      it[PreferencesKeys.REMINDER_SOUND] = sound
    }
  }

  //Main Settings

  override fun appTheme(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.APP_THEME] ?: AppTheme.DEFAULT
    }

  override suspend fun setAppTheme(theme: String) {
    dataStore.edit {
      it[PreferencesKeys.APP_THEME] = theme
    }
  }

}