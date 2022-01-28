package com.example.myapplication.repository

import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.data.roomdatabase.WaterDatabaseDao
import com.example.myapplication.utils.DateString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

//Repository is single source of truth for all data -- API and database
//Both retrofit and room are linked here to provide data access methods to the ViewModel
class WaterDataRepository @Inject constructor(
  private val waterDatabaseDao: WaterDatabaseDao
) {
  //Database Queries
  fun getDailyWaterRecord(date:String = DateString.getTodaysDate()): Flow<DailyWaterRecord>{
    return waterDatabaseDao.getDailyWaterRecord(date)
  }

  fun getDrinkLogs(date:String = DateString.getTodaysDate()): Flow<List<DrinkLogs>> {
    return waterDatabaseDao.getDrinkLogs(date).flowOn(Dispatchers.IO).conflate()
  }

  fun getDailyWaterRecordsList(startDate:String, endDate:String = DateString.getTodaysDate()): Flow<List<DailyWaterRecord>> {
    return waterDatabaseDao.getDailyWaterRecordsList(endDate,startDate)
  }

  suspend fun insertDailyWaterRecord(dailyWaterRecord: DailyWaterRecord): Long {
    return waterDatabaseDao.insertDailyWaterRecord(dailyWaterRecord)
  }

  suspend fun insertDrinkLog(drinkLog: DrinkLogs): Long {
    return waterDatabaseDao.insertDrinkLog(drinkLog)
  }

  suspend fun updateDailyWaterRecord(dailyWaterRecord: DailyWaterRecord){
    waterDatabaseDao.updateDailyWaterRecord(dailyWaterRecord)
  }

  suspend fun updateDrinkLog(drinkLog: DrinkLogs) {
    waterDatabaseDao.updateDrinkLog(drinkLog)
  }

  suspend fun deleteDrinkLog(drinkLog: DrinkLogs) {
    waterDatabaseDao.deleteDrinkLog(drinkLog)
  }

  suspend fun getDrinkLogsCount(): Int {
    return waterDatabaseDao.getDrinkLogsCount()
  }

  suspend fun getWaterRecordsCount(): Int {
    return waterDatabaseDao.getWaterRecordsCount()
  }

  suspend fun getCompletedWaterRecordsCount(): Int {
    return waterDatabaseDao.getCompletedWaterRecordsCount()
  }
}