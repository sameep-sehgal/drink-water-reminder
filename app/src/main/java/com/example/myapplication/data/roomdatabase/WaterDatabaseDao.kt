package com.example.myapplication.data.roomdatabase

import androidx.room.*
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.utils.DateString
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDatabaseDao {

    @Query("SELECT * FROM daily_water_record WHERE date = :date")
    fun getDailyWaterRecord(date:String = DateString.getTodaysDate()):Flow<DailyWaterRecord>

    @Query("SELECT * FROM daily_water_record WHERE date = :date")
    fun getDailyWaterRecordWithoutFlow(date:String = DateString.getTodaysDate()):DailyWaterRecord

    @Query("SELECT * FROM daily_water_record WHERE date BETWEEN :startDate AND :endDate")
    fun getDailyWaterRecordsList(endDate:String = DateString.getTodaysDate(), startDate:String):Flow<List<DailyWaterRecord>>

    @Query("SELECT * FROM drink_logs WHERE date = :date")
    fun getDrinkLogs(date:String = DateString.getTodaysDate()): Flow<List<DrinkLogs>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWaterRecord(dailyWaterRecord: DailyWaterRecord):Long
    //Long Return value is the row count of database where the object was inserted
    //If insertion failed then returns -1

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinkLog(drinkLog: DrinkLogs): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDailyWaterRecord(dailyWaterRecord: DailyWaterRecord)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDrinkLog(drinkLog: DrinkLogs)

    @Delete
    suspend fun deleteDrinkLog(drinkLog: DrinkLogs)

    @Query("SELECT COUNT(id) FROM drink_logs WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getDrinkLogsCount(startDate: String,endDate: String): Int

    @Query("SELECT COUNT(date) FROM daily_water_record")
    suspend fun getWaterRecordsCount(): Int

    @Query("SELECT COUNT(date) FROM daily_water_record WHERE is_goal_achieved = 1")
    suspend fun getCompletedWaterRecordsCount(): Int
}
