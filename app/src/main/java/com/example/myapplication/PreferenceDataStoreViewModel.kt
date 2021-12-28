package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.preferencedatastore.PreferenceDataStore
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

  val reminderSound = preferenceDataStore.reminderSound()

  fun setReminderSound(sound: String) {
    viewModelScope.launch {
      preferenceDataStore.setReminderSound(sound)
    }
  }

  //Main Settings

  val appTheme = preferenceDataStore.appTheme()

  fun setAppTheme(theme: String) {
    viewModelScope.launch {
      preferenceDataStore.setAppTheme(theme)
    }
  }
}