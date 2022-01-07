package com.example.myapplication.repository

import androidx.room.*
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.data.preferencedatastore.PreferenceDataStore
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
  private val waterDatabaseDao: WaterDatabaseDao,
  private val preferenceDataStore: PreferenceDataStore
) {
  //Database Queries
  fun getDailyWaterRecord(date:String = DateString.getTodaysDate()): Flow<DailyWaterRecord>{
    return waterDatabaseDao.getDailyWaterRecord(date)
  }

  fun getDrinkLogs(date:String = DateString.getTodaysDate()): Flow<List<DrinkLogs>> {
    return waterDatabaseDao.getDrinkLogs(date).flowOn(Dispatchers.IO).conflate()
  }

//  fun getFirstWaterRecord(): Flow<DailyWaterRecord> {
//    return waterDatabaseDao.getFirstWaterRecord()
//  }

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

  suspend fun getDrinkLogsCount(): Flow<Int> {
    return waterDatabaseDao.getDrinkLogsCount().flowOn(Dispatchers.IO).conflate()
  }

  suspend fun getWaterRecordsCount(): Flow<Int> {
    return waterDatabaseDao.getWaterRecordsCount().flowOn(Dispatchers.IO).conflate()
  }
}