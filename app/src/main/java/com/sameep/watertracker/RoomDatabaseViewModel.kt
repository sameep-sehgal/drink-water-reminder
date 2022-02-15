package com.sameep.watertracker

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sameep.watertracker.data.models.Beverage
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.data.models.DrinkLogs
import com.sameep.watertracker.repository.WaterDataRepository
import com.sameep.watertracker.utils.Beverages
import com.sameep.watertracker.utils.DateString
import com.sameep.watertracker.utils.RecommendedWaterIntake
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
    getDrinkLogs()
  }

  private val _drinkLogs = MutableStateFlow<List<DrinkLogs>>(emptyList())
  val drinkLogs : StateFlow<List<DrinkLogs>> =  _drinkLogs.asStateFlow()
  fun getDrinkLogs(
    startDate: String = DateString.getTodaysDate(),
    endDate: String = DateString.getTodaysDate()
  ){
    viewModelScope.launch(Dispatchers.IO) {
      waterDataRepository.getDrinkLogs(startDate, endDate).distinctUntilChanged()
        .catch { e->
          Log.e("getTodaysDrinkLogs", "error before collecting from Flow", )
        }
        .collect { it1 ->
          _drinkLogs.value = it1.sortedByDescending { it.time }
        }
    }
  }

  private val _statsDrinkLogsList = MutableStateFlow<List<DrinkLogs>>(emptyList())
  val statsDrinkLogsList : StateFlow<List<DrinkLogs>> =  _statsDrinkLogsList.asStateFlow()
  fun getStatsDrinkLogsList(
    startDate: String = DateString.getTodaysDate(),
    endDate: String = DateString.getTodaysDate()
  ){
    viewModelScope.launch(Dispatchers.IO) {
      waterDataRepository.getDrinkLogs(startDate, endDate).distinctUntilChanged()
        .catch { e->
          Log.e("getTodaysDrinkLogs", "error before collecting from Flow", )
        }
        .collect { it1 ->
          _statsDrinkLogsList.value = it1.sortedByDescending { it.time }
        }
    }
  }

  private val _selectedHistoryDrinkLogs = MutableStateFlow<List<DrinkLogs>>(emptyList())
  val selectedHistoryDrinkLogs : StateFlow<List<DrinkLogs>?> =  _selectedHistoryDrinkLogs.asStateFlow()

  fun getSelectedHistoryDrinkLogs(date:String){
    viewModelScope.launch(Dispatchers.IO) {
      waterDataRepository.getDrinkLogs(startDate = date, endDate = date).distinctUntilChanged()
        .catch { e->
          Log.e("getSelectedHistoryDLogs", "error before collecting from Flow")
        }
        .collect { it1 ->
          _selectedHistoryDrinkLogs.value = it1.sortedByDescending { it.time }
        }
    }
  }


  private val _todaysWaterRecord = MutableStateFlow(DailyWaterRecord(goal = 0, currWaterAmount = 0))
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

  private val _beverage = MutableStateFlow(Beverage(Beverages.DEFAULT, 0))
  val beverage : StateFlow<Beverage> = _beverage.asStateFlow()
  fun getBeverage(beverageName: String){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.getBeverage(beverageName).collect {
        _beverage.value = it
      }
    }
  }

  private val _beverageList = MutableStateFlow<List<Beverage>>(emptyList())
  val beverageList : StateFlow<List<Beverage>> =  _beverageList.asStateFlow()
  fun getAllBeverages(){
    viewModelScope.launch(Dispatchers.IO) {
      waterDataRepository.getAllBeverages().distinctUntilChanged()
        .collect { it1 ->
          _beverageList.value = it1.sortedBy { it2 -> it2.importance }
        }
    }
  }

  fun updateBeverageImportance(
    beverageList:List<Beverage>,
    importance:Int
  ) {
    viewModelScope.launch(Dispatchers.IO) {
      for(beverage in beverageList) {
        if(beverage.importance < importance){
          insertBeverage(
            Beverage(
              name = beverage.name,
              icon = beverage.icon,
              importance = beverage.importance + 1
            )
          )
        }
        if(beverage.importance == importance) {
          insertBeverage(
            Beverage(
              name = beverage.name,
              icon = beverage.icon,
              importance = 0
            )
          )
        }
      }
    }
  }

  fun updateBeverageImportanceOnDelete(
    beverageList:List<Beverage>,
    importance:Int
  ) {
    viewModelScope.launch(Dispatchers.IO) {
      for(beverage in beverageList) {
        if(beverage.importance > importance){
          insertBeverage(
            Beverage(
              name = beverage.name,
              icon = beverage.icon,
              importance = beverage.importance - 1
            )
          )
        }
      }
    }
  }

  private val _selectedHistoryWaterRecord = MutableStateFlow(DailyWaterRecord(goal = 0, currWaterAmount = 0))
  val selectedHistoryWaterRecord : StateFlow<DailyWaterRecord?> = _selectedHistoryWaterRecord.asStateFlow()
  fun getSelectedHistoryWaterRecord(date:String = DateString.getTodaysDate()){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.getDailyWaterRecord(date).collect {
        Log.d(TAG, "getDailyWaterRecord $it")
        _selectedHistoryWaterRecord.value = it
      }
    }
  }

  private val _waterRecord = MutableStateFlow(hashMapOf<String, DailyWaterRecord>())
  val waterRecord : StateFlow<HashMap<String, DailyWaterRecord>> = _waterRecord.asStateFlow()
  fun getDailyWaterRecord(date:String = DateString.getTodaysDate()){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.getDailyWaterRecord(date).collect {
        Log.d(TAG, "getDailyWaterRecord $it")
        _waterRecord.value[date] = it
      }
    }
  }
  private val _waterRecordsList = MutableStateFlow<List<DailyWaterRecord>>(emptyList())
  val waterRecordsList : StateFlow<List<DailyWaterRecord>> = _waterRecordsList.asStateFlow()
  fun getWaterRecordsList(
    startDate:String,
    endDate: String
  ){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.getDailyWaterRecordsList(
        startDate = startDate,
        endDate = endDate
      ).distinctUntilChanged()
        .catch {  }
        .collect {
          Log.d(TAG, "getWaterRecordsList: $it")
          _waterRecordsList.value = it
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

  fun insertBeverage(beverage: Beverage){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.insertBeverage(beverage)
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

  fun deleteDrinkLog(drinkLog: DrinkLogs){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.deleteDrinkLog(drinkLog)
    }
  }

  fun deleteBeverage(beverage: Beverage){
    viewModelScope.launch (Dispatchers.IO){
      waterDataRepository.deleteBeverage(beverage)
    }
  }

  private val _drinkLogsCount = MutableStateFlow(0)
  val drinkLogsCount : StateFlow<Int> = _drinkLogsCount.asStateFlow()
//  var drinkLogsCount:Int = 0
  fun getDrinkLogsCount(
    startDate: String,
    endDate: String
  ){
    viewModelScope.launch (Dispatchers.IO){
      _drinkLogsCount.value = waterDataRepository.getDrinkLogsCount(
        startDate = startDate,
        endDate = endDate
      )
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