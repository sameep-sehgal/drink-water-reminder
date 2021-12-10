package com.example.myapplication.ui.screens.hometab

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.data.preferencedatastore.PreferenceDataStore
import com.example.myapplication.repository.WaterDataRepository
import com.example.myapplication.utils.DateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeTabViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,//For Hilt
    private val waterDataRepository: WaterDataRepository,
    private val preferenceDataStore: PreferenceDataStore
) :ViewModel(){
    val TAG = HomeTabViewModel::class.simpleName
    //Create all the state variables and map repository methods
    //ViewModel methods will be used in the UI

    //Initialize state required for app to run in the constructor
    init {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceDataStore.recommendedWaterIntake().distinctUntilChanged()
                .collect {
                    _savedKey.value = it
                }
        }

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
                    if(it.isNullOrEmpty()) Log.d("getTodaysDrinkLogs",
                        "Today's Drink Logs List is empty")
                    else {
                        _drinkLogs.value = it.sortedByDescending { it.time }
                    }
                }
        }
    }

    private val _savedKey = MutableStateFlow<Int>(0)
    val savedKey : StateFlow<Int> = _savedKey.asStateFlow()

    fun getSavedKey(){
        viewModelScope.launch (Dispatchers.IO){
            preferenceDataStore.recommendedWaterIntake().collect {
                _savedKey.value = it
            }
        }
    }

    fun setSavedKey(key: Int) {
        viewModelScope.launch {
            preferenceDataStore.setRecommendedWaterIntake(key)
        }
    }

    private val _waterRecord = MutableStateFlow<DailyWaterRecord>(DailyWaterRecord(goal = 2900, currWaterAmount = 1400))
    val waterRecord : StateFlow<DailyWaterRecord> = _waterRecord.asStateFlow()
    fun getDailyWaterRecord(date:String = DateString.getTodaysDate()){
        viewModelScope.launch (Dispatchers.IO){
            waterDataRepository.getDailyWaterRecord(date).collect {
                Log.d(TAG, "getDailyWaterRecord $it")
                if(it === null) {
                    Log.d(TAG, "getDailyWaterRecord $it")
                    insertDailyWaterRecord(DailyWaterRecord(goal = savedKey.value))
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

    fun insertDrinkLog(drinkLog: DrinkLogs){
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
            waterDataRepository.deleteDrinkLog(drinkLog = drinkLog)
        }
    }
}