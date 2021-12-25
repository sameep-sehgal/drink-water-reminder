package com.example.myapplication

import android.util.Log
import androidx.compose.animation.core.updateTransition
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.data.preferencedatastore.PreferenceDataStore
import com.example.myapplication.repository.WaterDataRepository
import com.example.myapplication.utils.DateString
import com.example.myapplication.utils.RecommendedWaterIntake
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomDatabaseViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle,//For Hilt
  private val waterDataRepository: WaterDataRepository
) :ViewModel(){
  val TAG = RoomDatabaseViewModel::class.simpleName
  //Create all the state variables and map repository methods
  //ViewModel methods will be used in the UI

  //Initialize state required for app to run in the constructor
  init {
    getDailyWaterRecord()
    getTodaysDrinkLogs()
  }

  private val _drinkLogs = MutableStateFlow<List<DrinkLogs>>(emptyList())
  val drinkLogs : StateFlow<List<DrinkLogs>> =  _drinkLogs.asStateFlow()

  private fun getTodaysDrinkLogs(){
    viewModelScope.launch(Dispatchers.IO) {
      waterDataRepository.getDrinkLogs().distinctUntilChanged()
        .catch { e->
          Log.e("getTodaysDrinkLogs", "error before collecting from Flow", )
        }
        .collect {
          _drinkLogs.value = it.sortedByDescending { it.time }
        }
    }
  }


  private val _waterRecord = MutableStateFlow<DailyWaterRecord>(DailyWaterRecord(goal = 0, currWaterAmount = 0))
  val waterRecord : StateFlow<DailyWaterRecord> = _waterRecord.asStateFlow()
  fun getDailyWaterRecord(date:String = DateString.getTodaysDate()){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.getDailyWaterRecord(date).collect {
        Log.d(TAG, "getDailyWaterRecord $it")
        if(it === null) {
          Log.d(TAG, "getDailyWaterRecord $it")
          insertDailyWaterRecord(DailyWaterRecord(goal = RecommendedWaterIntake.NOT_SET))
          getDailyWaterRecord()
        }else {
          _waterRecord.value = it
        }
      }
    }
  }

  fun insertDailyWaterRecord(waterRecord: DailyWaterRecord){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.insertDailyWaterRecord(waterRecord)
    }
  }

  fun insertDrinkLog(
    drinkLog: DrinkLogs,
    dailyWaterRecord: DailyWaterRecord
  ){
    viewModelScope.launch(Dispatchers.IO) {
      waterDataRepository.insertDrinkLog(drinkLog = drinkLog)
    }
  }

  fun updateDailyWaterRecord(dailyWaterRecord: DailyWaterRecord){
    viewModelScope.launch(Dispatchers.IO) {
      waterDataRepository.updateDailyWaterRecord(dailyWaterRecord)
    }
  }

  fun updateDrinkLog(drinkLog: DrinkLogs){
    viewModelScope.launch(Dispatchers.IO) {
      waterDataRepository.updateDrinkLog(drinkLog)
    }
  }

  fun deleteDrinkLog(
    drinkLog: DrinkLogs,
    dailyWaterRecord: DailyWaterRecord
  ){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.deleteDrinkLog(drinkLog = drinkLog)
    }
  }
}