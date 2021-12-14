package com.example.myapplication.ui.screens.settingstab

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.preferencedatastore.PreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsTabViewModel @Inject constructor(
  private val preferenceDataStore: PreferenceDataStore
)
  :ViewModel(){

}