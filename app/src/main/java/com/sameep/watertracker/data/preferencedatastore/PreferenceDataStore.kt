package com.sameep.watertracker.data.preferencedatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.sameep.watertracker.utils.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_storage")

class PreferenceDataStore @Inject constructor(@ApplicationContext context:Context)
    : PreferenceDataStoreInterface {

  private val dataStore = context.dataStore

  //preference keys
  object PreferencesKeys {
    val IS_USER_INFO_COLLECTED = booleanPreferencesKey("is_user_info_collected")
    val FIRST_WATER_DATA_DATE = stringPreferencesKey("first_water_data_date")
    val BEVERAGE = stringPreferencesKey("beverage")
    val SWITCH_NOTIFICATION_ON_DIALOG_LAST_SHOWN_DATE = stringPreferencesKey("switch_notification_on_dialog_last_shown_date")
    val IS_RATING_DIALOG_SHOWN = booleanPreferencesKey("is_rating_dialog_shown")

    //Personal Settings
    val GENDER = stringPreferencesKey("gender")
    val WEIGHT = intPreferencesKey("weight")
    val ACTIVITY_LEVEL = stringPreferencesKey("activity_level")
    val WEATHER = stringPreferencesKey("weather")
    val WEIGHT_UNIT = stringPreferencesKey("weight_unit")
    val WATER_UNIT = stringPreferencesKey("water_unit")

    //How much water to drink
    val IS_DAILY_WATER_GOAL_TRACKING_RECOMMENDED_INTAKE = booleanPreferencesKey("is_daily_water_goal_tracking_recommended_intake")
    val RECOMMENDED_WATER_INTAKE = intPreferencesKey("recommended_water_intake")
    val DAILY_WATER_GOAL = intPreferencesKey("daily_water_goal")

    //Reminder Settings
    val IS_REMINDER_ON = booleanPreferencesKey("is_reminder_on")
    val REMINDER_PERIOD_START = stringPreferencesKey("reminder_period_start")
    val REMINDER_PERIOD_END = stringPreferencesKey("reminder_period_end")
    val REMINDER_GAP = intPreferencesKey("reminder_gap")
    val REMINDER_AFTER_GOAL_ACHIEVED = booleanPreferencesKey("reminder_after_goal_achieved")
    val REMINDER_SOUND = stringPreferencesKey("reminder_sound")

    //Container Settings
    val GLASS_CAPACITY = intPreferencesKey("glass_capacity")
    val MUG_CAPACITY = intPreferencesKey("mug_capacity")
    val BOTTLE_CAPACITY = intPreferencesKey("bottle_capacity")
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

  override fun beverage(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.BEVERAGE] ?: Beverages.DEFAULT
    }

  override suspend fun setBeverage(selectedBeverage: String) {
    dataStore.edit {
      it[PreferencesKeys.BEVERAGE] = selectedBeverage
    }
  }

  override fun switchNotificationOnDialogLastShownDate(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.SWITCH_NOTIFICATION_ON_DIALOG_LAST_SHOWN_DATE] ?: DateString.getYesterdaysDate()
    }

  override suspend fun setSwitchNotificationOnDialogLastShownDate(value: String) {
    dataStore.edit {
      it[PreferencesKeys.SWITCH_NOTIFICATION_ON_DIALOG_LAST_SHOWN_DATE] = value
    }
  }

  override fun isRatingDialogShown(): Flow<Boolean> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.IS_RATING_DIALOG_SHOWN] ?: false
    }

  override suspend fun setIsRatingDialogShown(value: Boolean) {
    dataStore.edit {
      it[PreferencesKeys.IS_RATING_DIALOG_SHOWN] = value
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
      it[PreferencesKeys.GENDER] ?: Gender.MALE
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
      it[PreferencesKeys.WEIGHT] ?: Weight.DEFAULT_WEIGHT_KG
    }

  override suspend fun setWeight(amount: Int) {
    dataStore.edit {
      it[PreferencesKeys.WEIGHT] = amount
    }
  }

  override fun activityLevel(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.ACTIVITY_LEVEL] ?: ActivityLevel.LIGHTLY_ACTIVE
    }

  override suspend fun setActivityLevel(activityLevel: String) {
    dataStore.edit {
      it[PreferencesKeys.ACTIVITY_LEVEL] = activityLevel
    }
  }

  override fun weather(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.WEATHER] ?: Weather.NORMAL
    }

  override suspend fun setWeather(weather: String) {
    dataStore.edit {
      it[PreferencesKeys.WEATHER] = weather
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

  //Your Water Intake Settings

  override fun isDailyWaterGoalTrackingRecommendedIntake(): Flow<Boolean> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.IS_DAILY_WATER_GOAL_TRACKING_RECOMMENDED_INTAKE] ?: true
    }

  override suspend fun setIsDailyWaterGoalTrackingRecommendedIntake(value: Boolean) {
    dataStore.edit {
      it[PreferencesKeys.IS_DAILY_WATER_GOAL_TRACKING_RECOMMENDED_INTAKE] = value
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

  override fun isReminderOn(): Flow<Boolean> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.IS_REMINDER_ON] ?: true
    }

  override suspend fun setIsReminderOn(value: Boolean) {
    dataStore.edit {
      it[PreferencesKeys.IS_REMINDER_ON] = value
    }
  }

  override fun reminderPeriodStart(): Flow<String> =
    dataStore.data.catch {
      if (it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }.map {
      it[PreferencesKeys.REMINDER_PERIOD_START] ?: ReminderPeriod.DEFAULT_REMINDER_PERIOD_START
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
      it[PreferencesKeys.REMINDER_PERIOD_END] ?: ReminderPeriod.DEFAULT_REMINDER_PERIOD_END
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
      it[PreferencesKeys.REMINDER_GAP] ?: ReminderGap.ONE_HOUR
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
      it[PreferencesKeys.REMINDER_SOUND] ?: ""
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
      it[PreferencesKeys.GLASS_CAPACITY] ?: Container.baseGlassCapacity(Units.ML)
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
      it[PreferencesKeys.MUG_CAPACITY] ?: Container.baseMugCapacity(Units.ML)
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
      it[PreferencesKeys.BOTTLE_CAPACITY] ?: Container.baseBottleCapacity(Units.ML)
    }

  override suspend fun setBottleCapacity(capacity: Int) {
    dataStore.edit {
      it[PreferencesKeys.BOTTLE_CAPACITY] = capacity
    }
  }

}