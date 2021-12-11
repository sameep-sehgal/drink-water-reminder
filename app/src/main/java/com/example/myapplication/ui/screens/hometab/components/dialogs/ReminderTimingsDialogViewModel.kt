package com.example.myapplication.ui.screens.hometab.components.dialogs

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.data.models.ReminderTimings
import com.example.myapplication.repository.ReminderRepository
import com.example.myapplication.repository.WaterDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderTimingsDialogViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle,//For Hilt
  private val reminderRepository: ReminderRepository
): ViewModel(){
  val TAG = ReminderTimingsDialogViewModel::class.simpleName

  init{
    getReminderTimings()
  }

  private val _reminderTimings = MutableStateFlow<List<ReminderTimings>>(emptyList())
  val reminderTimings : StateFlow<List<ReminderTimings>> =  _reminderTimings.asStateFlow()

  private fun getReminderTimings() {
    viewModelScope.launch (Dispatchers.IO){
      reminderRepository.getReminderTimings().distinctUntilChanged()
        .catch { e->
          Log.e(TAG, "error before collecting from Flow", )
        }
        .collect {
          if(it.isNullOrEmpty()) Log.d("getTodaysDrinkLogs",
            "Today's Drink Logs List is empty")
          else {
            _reminderTimings.value = it.sortedBy { it.time }
          }
        }
    }
  }

  fun insertReminderTiming(reminderTiming: ReminderTimings){
    viewModelScope.launch (Dispatchers.IO) {
      reminderRepository.insertReminderTiming(reminderTiming = reminderTiming)
    }
  }

  fun deleteReminderTiming(reminderTiming: ReminderTimings){
    viewModelScope.launch (Dispatchers.IO) {
      reminderRepository.deleteReminderTiming(reminderTiming = reminderTiming)
    }
  }

  fun updateReminderTiming(reminderTiming: ReminderTimings){
    viewModelScope.launch (Dispatchers.IO) {
      reminderRepository.updateReminderTiming(reminderTiming = reminderTiming)
    }
  }

}