package com.sameep.watertracker.repository

import com.sameep.watertracker.data.models.Beverage
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.data.models.DrinkLogs
import com.sameep.watertracker.data.roomdatabase.WaterDatabaseDao
import com.sameep.watertracker.utils.DateString
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

  fun getDrinkLogs(
    startDate: String = DateString.getTodaysDate(),
    endDate: String = DateString.getTodaysDate()
  ): Flow<List<DrinkLogs>> {
    return waterDatabaseDao.getDrinkLogs(startDate, endDate).flowOn(Dispatchers.IO).conflate()
  }

  fun getDailyWaterRecordsList(startDate:String, endDate:String = DateString.getTodaysDate()): Flow<List<DailyWaterRecord>> {
    return waterDatabaseDao.getDailyWaterRecordsList(endDate,startDate)
  }

  fun getAllBeverages(): Flow<List<Beverage>> {
    return waterDatabaseDao.getAllBeverages()
  }

  fun getBeverage(beverageName: String): Flow<Beverage> {
    return waterDatabaseDao.getBeverage(beverageName)
  }

  suspend fun insertDailyWaterRecord(dailyWaterRecord: DailyWaterRecord): Long {
    return waterDatabaseDao.insertDailyWaterRecord(dailyWaterRecord)
  }

  suspend fun insertDrinkLog(drinkLog: DrinkLogs): Long {
    return waterDatabaseDao.insertDrinkLog(drinkLog)
  }

  suspend fun insertBeverage(beverage: Beverage): Long {
    return waterDatabaseDao.insertBeverage(beverage)
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

  suspend fun deleteBeverage(beverage: Beverage) {
    waterDatabaseDao.deleteBeverage(beverage)
  }

  suspend fun getDrinkLogsCount(
    startDate: String,
    endDate: String
  ): Int {
    return waterDatabaseDao.getDrinkLogsCount(
      startDate = startDate,
      endDate = endDate
    )
  }

  suspend fun getWaterRecordsCount(): Int {
    return waterDatabaseDao.getWaterRecordsCount()
  }

  suspend fun getCompletedWaterRecordsCount(): Int {
    return waterDatabaseDao.getCompletedWaterRecordsCount()
  }
}