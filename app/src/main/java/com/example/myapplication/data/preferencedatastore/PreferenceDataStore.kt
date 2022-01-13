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
    val FIRST_WATER_DATA_DATE = stringPreferencesKey("first_water_data_date")

    //Personal Settings
    val GENDER = stringPreferencesKey("gender")
    val WEIGHT = intPreferencesKey("weight")
    val WEIGHT_UNIT = stringPreferencesKey("weight_unit")
    val WATER_UNIT = stringPreferencesKey("water_unit")
    val RECOMMENDED_WATER_INTAKE = intPreferencesKey("recommended_water_intake")
    val DAILY_WATER_GOAL = intPreferencesKey("daily_water_goal")

    //Reminder Settings
    val REMINDER_PERIOD_START = stringPreferencesKey("reminder_period_start")
    val REMINDER_PERIOD_END = stringPreferencesKey("reminder_period_end")
    val REMINDER_GAP = intPreferencesKey("reminder_gap")
    val REMINDER_AFTER_GOAL_ACHIEVED = booleanPreferencesKey("reminder_after_goal_achieved")
    val REMINDER_SOUND = stringPreferencesKey("reminder_sound")

    //Container Settings
    val GLASS_CAPACITY = intPreferencesKey("glass_capacity")
    val MUG_CAPACITY = intPreferencesKey("mug_capacity")
    val BOTTLE_CAPACITY = intPreferencesKey("bottle_capacity")

    //Main Settings
    val APP_THEME = stringPreferencesKey("app_theme")
  }

  fun reminderPeriodStartWithoutFlow() : String {
    var res:String = ""
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.REMINDER_PERIOD_START] ?: ReminderPeriod.NOT_SET
    }.map {
      res = it
    }
    return res
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

  override fun firstWaterDataDate(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.FIRST_WATER_DATA_DATE] ?: DateString.NOT_SET
    }

  override suspend fun setFirstWaterDataDate(date: String) {
    dataStore.edit {
      it[PreferencesKeys.FIRST_WATER_DATA_DATE] = date
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

  override fun weightUnit(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.WEIGHT_UNIT] ?: Units.KG
    }

  override suspend fun setWeightUnit(unit: String) {
    dataStore.edit {
      it[PreferencesKeys.WEIGHT_UNIT] = unit
    }
  }

  override fun waterUnit(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.WATER_UNIT] ?: Units.ML
    }

  override suspend fun setWaterUnit(unit: String)  {
    dataStore.edit {
      it[PreferencesKeys.WATER_UNIT] = unit
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

  //Container Settings
  override fun glassCapacity(): Flow<Int> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.GLASS_CAPACITY] ?: Container.baseGlassCapacityInMl
    }

  override suspend fun setGlassCapacity(capacity: Int) {
    dataStore.edit {
      it[PreferencesKeys.GLASS_CAPACITY] = capacity
    }
  }

  override fun mugCapacity(): Flow<Int> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.MUG_CAPACITY] ?: Container.baseMugCapacityInMl
    }

  override suspend fun setMugCapacity(capacity: Int) {
    dataStore.edit {
      it[PreferencesKeys.MUG_CAPACITY] = capacity
    }
  }

  override fun bottleCapacity(): Flow<Int> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.BOTTLE_CAPACITY] ?: Container.baseBottleCapacityInMl
    }

  override suspend fun setBottleCapacity(capacity: Int) {
    dataStore.edit {
      it[PreferencesKeys.BOTTLE_CAPACITY] = capacity
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