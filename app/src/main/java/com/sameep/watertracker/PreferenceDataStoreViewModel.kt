package com.sameep.watertracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sameep.watertracker.data.preferencedatastore.PreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferenceDataStoreViewModel @Inject constructor(
  private val preferenceDataStore: PreferenceDataStore
) :ViewModel(){

  val isUserInfoCollected = preferenceDataStore.isUserInfoCollected().asLiveData()

  fun setIsUserInfoCollected(value: Boolean) {
    viewModelScope.launch {
      preferenceDataStore.setIsUserInfoCollected(value)
    }
  }

  val firstWaterDataDate = preferenceDataStore.firstWaterDataDate()

  fun setFirstWaterDataDate(date: String) {
    viewModelScope.launch {
      preferenceDataStore.setFirstWaterDataDate(date)
    }
  }

  val beverage = preferenceDataStore.beverage()

  fun setBeverage(selectedBeverage: String) {
    viewModelScope.launch {
      preferenceDataStore.setBeverage(selectedBeverage)
    }
  }

  val switchNotificationOnDialogLastShownDate = preferenceDataStore.switchNotificationOnDialogLastShownDate()

  fun setSwitchNotificationOnDialogLastShownDate(value: String) {
    viewModelScope.launch {
      preferenceDataStore.setSwitchNotificationOnDialogLastShownDate(value)
    }
  }

  val isRatingDialogShown = preferenceDataStore.isRatingDialogShown()

  fun setIsRatingDialogShown(value: Boolean) {
    viewModelScope.launch {
      preferenceDataStore.setIsRatingDialogShown(value)
    }
  }

  //Personal Settings

  val gender = preferenceDataStore.gender()

  fun setGender(gender: String) {
    viewModelScope.launch {
      preferenceDataStore.setGender(gender)
    }
  }

  val weight = preferenceDataStore.weight()

  fun setWeight(weight: Int) {
    viewModelScope.launch {
      preferenceDataStore.setWeight(weight)
    }
  }

  val activityLevel = preferenceDataStore.activityLevel()

  fun setActivityLevel(activityLevel: String) {
    viewModelScope.launch {
      preferenceDataStore.setActivityLevel(activityLevel)
    }
  }

  val weather = preferenceDataStore.weather()

  fun setWeather(weather: String) {
    viewModelScope.launch {
      preferenceDataStore.setWeather(weather)
    }
  }

  val weightUnit = preferenceDataStore.weightUnit()

  fun setWeightUnit(unit: String) {
    viewModelScope.launch {
      preferenceDataStore.setWeightUnit(unit)
    }
  }

  val waterUnit = preferenceDataStore.waterUnit()

  fun setWaterUnit(unit: String) {
    viewModelScope.launch {
      preferenceDataStore.setWaterUnit(unit)
    }
  }

  //How much water to drink

  val isDailyWaterGoalTrackingRecommendedIntake = preferenceDataStore.isDailyWaterGoalTrackingRecommendedIntake()

  fun setIsDailyWaterGoalTrackingRecommendedIntake(value: Boolean) {
    viewModelScope.launch {
      preferenceDataStore.setIsDailyWaterGoalTrackingRecommendedIntake(value)
    }
  }

  val recommendedWaterIntake = preferenceDataStore.recommendedWaterIntake()

  fun setRecommendedWaterIntake(amount: Int) {
    viewModelScope.launch {
      preferenceDataStore.setRecommendedWaterIntake(amount)
    }
  }

  val dailyWaterGoal = preferenceDataStore.dailyWaterGoal()

  fun setDailyWaterGoal(amount: Int) {
    viewModelScope.launch {
      preferenceDataStore.setDailyWaterGoal(amount)
    }
  }

  //Reminder Settings

  val isReminderOn = preferenceDataStore.isReminderOn()

  fun setIsReminderOn(value: Boolean) {
    viewModelScope.launch {
      preferenceDataStore.setIsReminderOn(value)
    }
  }

  val reminderPeriodStart = preferenceDataStore.reminderPeriodStart()

  fun setReminderPeriodStart(value: String) {
    viewModelScope.launch {
      preferenceDataStore.setReminderPeriodStart(value)
    }
  }

  val reminderPeriodEnd = preferenceDataStore.reminderPeriodEnd()

  fun setReminderPeriodEnd(value: String) {
    viewModelScope.launch {
      preferenceDataStore.setReminderPeriodEnd(value)
    }
  }

  val reminderGap = preferenceDataStore.reminderGap()

  fun setReminderGap(gapTime: Int) {
    viewModelScope.launch {
      preferenceDataStore.setReminderGap(gapTime)
    }
  }

  val reminderAfterGoalAchieved = preferenceDataStore.reminderAfterGoalAchieved()

  fun setReminderAfterGoalAchieved(value: Boolean) {
    viewModelScope.launch {
      preferenceDataStore.setReminderAfterGoalAchieved(value)
    }
  }

  //Container Settings

  val glassCapacity = preferenceDataStore.glassCapacity()

  fun setGlassCapacity(capacity: Int) {
    viewModelScope.launch {
      preferenceDataStore.setGlassCapacity(capacity)
    }
  }

  val mugCapacity = preferenceDataStore.mugCapacity()

  fun setMugCapacity(capacity: Int) {
    viewModelScope.launch {
      preferenceDataStore.setMugCapacity(capacity)
    }
  }

  val bottleCapacity = preferenceDataStore.bottleCapacity()

  fun setBottleCapacity(capacity: Int) {
    viewModelScope.launch {
      preferenceDataStore.setBottleCapacity(capacity)
    }
  }
}