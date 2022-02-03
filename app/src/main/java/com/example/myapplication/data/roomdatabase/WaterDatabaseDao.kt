package com.example.myapplication.data.roomdatabase

import androidx.room.*
import com.example.myapplication.data.models.Beverage
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

    @Query("SELECT * FROM beverages")
    fun getAllBeverages(): Flow<List<Beverage>>

    @Query("SELECT * FROM beverages WHERE name = :name")
    fun getBeverage(name: String): Flow<Beverage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWaterRecord(dailyWaterRecord: DailyWaterRecord):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinkLog(drinkLog: DrinkLogs): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeverage(beverage: Beverage):Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDailyWaterRecord(dailyWaterRecord: DailyWaterRecord)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDrinkLog(drinkLog: DrinkLogs)

    @Delete
    suspend fun deleteDrinkLog(drinkLog: DrinkLogs)

    @Delete
    suspend fun deleteBeverage(beverage: Beverage)

    @Query("SELECT COUNT(id) FROM drink_logs WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getDrinkLogsCount(startDate: String,endDate: String): Int

    @Query("SELECT COUNT(date) FROM daily_water_record")
    suspend fun getWaterRecordsCount(): Int

    @Query("SELECT COUNT(date) FROM daily_water_record WHERE is_goal_achieved = 1")
    suspend fun getCompletedWaterRecordsCount(): Int
}
