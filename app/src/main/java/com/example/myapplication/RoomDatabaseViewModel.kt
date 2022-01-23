package com.example.myapplication

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
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

  fun refreshData() {
    getTodaysWaterRecord()
    getTodaysDrinkLogs()
    getSelectedHistoryDrinkLogs(DateString.getTodaysDate())
    getSelectedHistoryWaterRecord(DateString.getTodaysDate())
  }

  private val _drinkLogs = MutableStateFlow<List<DrinkLogs>>(emptyList())
  val drinkLogs : StateFlow<List<DrinkLogs>> =  _drinkLogs.asStateFlow()
  private fun getTodaysDrinkLogs(){
    viewModelScope.launch(Dispatchers.IO) {
      waterDataRepository.getDrinkLogs().distinctUntilChanged()
        .catch { e->
          Log.e("getTodaysDrinkLogs", "error before collecting from Flow", )
        }
        .collect { it1 ->
          _drinkLogs.value = it1.sortedByDescending { it.time }
        }
    }
  }

  private val _selectedHistoryDrinkLogs = MutableStateFlow<List<DrinkLogs>>(emptyList())
  val selectedHistoryDrinkLogs : StateFlow<List<DrinkLogs>> =  _selectedHistoryDrinkLogs.asStateFlow()

  fun getSelectedHistoryDrinkLogs(date:String){
    viewModelScope.launch(Dispatchers.IO) {
      waterDataRepository.getDrinkLogs(date).distinctUntilChanged()
        .catch { e->
          Log.e("getSelectedHistoryDLogs", "error before collecting from Flow")
        }
        .collect { it1 ->
          _selectedHistoryDrinkLogs.value = it1.sortedByDescending { it.time }
        }
    }
  }


  private val _todaysWaterRecord = MutableStateFlow(DailyWaterRecord(goal = RecommendedWaterIntake.NOT_SET, currWaterAmount = 0))
  val todaysWaterRecord : StateFlow<DailyWaterRecord> = _todaysWaterRecord.asStateFlow()
  private fun getTodaysWaterRecord(date:String = DateString.getTodaysDate()){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.getDailyWaterRecord(date).collect {
        Log.d(TAG, "getDailyWaterRecord $it")
        if(it === null) {
          Log.d(TAG, "getDailyWaterRecord $it")
          insertDailyWaterRecord(DailyWaterRecord(goal = RecommendedWaterIntake.NOT_SET))
          getTodaysWaterRecord()
        }else {
          _todaysWaterRecord.value = it
        }
      }
    }
  }

  private val _selectedHistoryWaterRecord = MutableStateFlow<DailyWaterRecord>(DailyWaterRecord(goal = 0, currWaterAmount = 0))
  val selectedHistoryWaterRecord : StateFlow<DailyWaterRecord> = _selectedHistoryWaterRecord.asStateFlow()
  fun getSelectedHistoryWaterRecord(date:String = DateString.getTodaysDate()){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.getDailyWaterRecord(date).collect {
        Log.d(TAG, "getDailyWaterRecord $it")
        _selectedHistoryWaterRecord.value = it
      }
    }
  }

  private val _waterRecord = MutableStateFlow(hashMapOf<String,DailyWaterRecord>())
  val waterRecord : StateFlow<HashMap<String,DailyWaterRecord>> = _waterRecord.asStateFlow()
  fun getDailyWaterRecord(date:String = DateString.getTodaysDate()){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.getDailyWaterRecord(date).collect {
        Log.d(TAG, "getDailyWaterRecord $it")
        val temp = _waterRecord.value
        temp[date] = it
        _waterRecord.value = temp
      }
    }
  }

  private val _weekWaterRecordsList = MutableStateFlow<List<DailyWaterRecord>>(emptyList())
  val weekWaterRecordsList : StateFlow<List<DailyWaterRecord>> =  _weekWaterRecordsList.asStateFlow()
  fun getWeekWaterRecordsList(
    endDate:String = DateString.getTodaysDate(),
    startDate:String = DateString.reduceDays(endDate,7)
  ){
    viewModelScope.launch(Dispatchers.IO) {
      waterDataRepository.getDailyWaterRecordsList(endDate, startDate).distinctUntilChanged()
        .catch { e->
          Log.e("getWaterRecordsList", "error before collecting from Flow", )
        }
        .collect { it1 ->
          _weekWaterRecordsList.value = it1.sortedByDescending { it.date }
        }
    }
  }

  private val _monthWaterRecordsList = MutableStateFlow<List<DailyWaterRecord>>(emptyList())
  val monthWaterRecordsList : StateFlow<List<DailyWaterRecord>> =  _monthWaterRecordsList.asStateFlow()
  fun getMonthWaterRecordsList(
    endDate:String = DateString.getTodaysDate(),
    startDate:String = DateString.reduceDays(endDate,30)
  ){
    viewModelScope.launch(Dispatchers.IO) {
      waterDataRepository.getDailyWaterRecordsList(endDate, startDate).distinctUntilChanged()
        .catch { e->
          Log.e("getWaterRecordsList", "error before collecting from Flow")
        }
        .collect { it1 ->
          _monthWaterRecordsList.value = it1.sortedByDescending { it.date }
        }
    }
  }

//  private val _firstWaterRecord = MutableStateFlow<DailyWaterRecord>(DailyWaterRecord(goal = 0, currWaterAmount = 0))
//  val firstWaterRecord : StateFlow<DailyWaterRecord> = _waterRecord.asStateFlow()
//  fun getFirstWaterRecord() {
//    viewModelScope.launch(Dispatchers.IO) {
//      waterDataRepository.getFirstWaterRecord().collect {
//        _firstWaterRecord.value = it
//      }
//    }
//  }

  fun insertDailyWaterRecord(waterRecord: DailyWaterRecord){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.insertDailyWaterRecord(waterRecord)
    }
  }

  fun insertDrinkLog(
    drinkLog: DrinkLogs
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
    drinkLog: DrinkLogs
  ){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.deleteDrinkLog(drinkLog = drinkLog)
    }
  }

  private val _drinkLogsCount = MutableStateFlow(0)
  val drinkLogsCount : StateFlow<Int> = _drinkLogsCount.asStateFlow()
//  var drinkLogsCount:Int = 0
  fun getDrinkLogsCount(){
    viewModelScope.launch (Dispatchers.IO){
      _drinkLogsCount.value = waterDataRepository.getDrinkLogsCount()
    }
  }

  private val _waterRecordsCount = MutableStateFlow(1)
  val waterRecordsCount : StateFlow<Int> = _waterRecordsCount.asStateFlow()
  fun getWaterRecordsCount(){
    viewModelScope.launch (Dispatchers.IO){
      _waterRecordsCount.value = waterDataRepository.getWaterRecordsCount()
    }
  }

  private val _completedWaterRecordsCount = MutableStateFlow(0)
  val completedWaterRecordsCount : StateFlow<Int> = _completedWaterRecordsCount.asStateFlow()
  fun getCompletedWaterRecordsCount(){
    viewModelScope.launch (Dispatchers.IO){
      _completedWaterRecordsCount.value = waterDataRepository.getCompletedWaterRecordsCount()
    }
  }
}