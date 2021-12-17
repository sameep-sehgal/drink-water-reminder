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

  //Personal Settings

  val gender = preferenceDataStore.gender().asLiveData()

  fun setGender(gender: String) {
    viewModelScope.launch {
      preferenceDataStore.setGender(gender)
    }
  }

  val weight = preferenceDataStore.weight().asLiveData()

  fun setWeight(weight: Int) {
    viewModelScope.launch {
      preferenceDataStore.setWeight(weight)
    }
  }

  val units = preferenceDataStore.weight_unit().asLiveData()

  fun setUnits(units: String) {
    viewModelScope.launch {
      preferenceDataStore.setWeightUnit(units)
    }
  }

  val recommendedWaterIntake = preferenceDataStore.recommendedWaterIntake().asLiveData()

  fun setRecommendedWaterIntake(amount: Int) {
    viewModelScope.launch {
      preferenceDataStore.setRecommendedWaterIntake(amount)
    }
  }

  val dailyWaterGoal = preferenceDataStore.dailyWaterGoal().asLiveData()

  fun setDailyWaterGoal(amount: Int) {
    viewModelScope.launch {
      preferenceDataStore.setDailyWaterGoal(amount)
    }
  }

  //Reminder Settings

  val reminderPeriodStart = preferenceDataStore.reminderPeriodStart().asLiveData()

  fun setReminderPeriodStart(value: String) {
    viewModelScope.launch {
      preferenceDataStore.setReminderPeriodStart(value)
    }
  }

  val reminderPeriodEnd = preferenceDataStore.reminderPeriodEnd().asLiveData()

  fun setReminderPeriodEnd(value: String) {
    viewModelScope.launch {
      preferenceDataStore.setReminderPeriodEnd(value)
    }
  }

  val reminderGap = preferenceDataStore.reminderGap().asLiveData()

  fun setReminderGap(gapTime: Int) {
    viewModelScope.launch {
      preferenceDataStore.setReminderGap(gapTime)
    }
  }

  val reminderAfterGoalAchieved = preferenceDataStore.reminderAfterGoalAchieved().asLiveData()

  fun setReminderAfterGoalAchieved(value: Boolean) {
    viewModelScope.launch {
      preferenceDataStore.setReminderAfterGoalAchieved(value)
    }
  }

  val reminderSound = preferenceDataStore.reminderSound().asLiveData()

  fun setReminderSound(sound: String) {
    viewModelScope.launch {
      preferenceDataStore.setReminderSound(sound)
    }
  }

  //Main Settings

  val appTheme = preferenceDataStore.appTheme().asLiveData()

  fun setAppTheme(theme: String) {
    viewModelScope.launch {
      preferenceDataStore.setAppTheme(theme)
    }
  }
}