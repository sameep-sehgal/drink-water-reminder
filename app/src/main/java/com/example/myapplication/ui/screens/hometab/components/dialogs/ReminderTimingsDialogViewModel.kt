package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.repository.WaterDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReminderTimingsDialogViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle,//For Hilt
  waterDataRepository: WaterDataRepository
): ViewModel(){
  val TAG = ReminderTimingsDialogViewModel::class.simpleName

  init{

  }


}