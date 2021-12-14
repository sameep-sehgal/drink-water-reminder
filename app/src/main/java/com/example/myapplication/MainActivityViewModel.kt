package com.example.myapplication

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.preferencedatastore.PreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
  private val preferenceDataStore: PreferenceDataStore
) :ViewModel(){

  val isUserInfoCollected = preferenceDataStore.isUserInfoCollected().asLiveData()

  fun setIsUserInfoCollected(value: Boolean) {
    viewModelScope.launch {
      preferenceDataStore.setIsUserInfoCollected(value)
    }
  }
}