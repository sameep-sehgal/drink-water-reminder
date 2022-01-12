package com.example.myapplication.data.roomdatabase

import androidx.room.*
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.data.models.DrinkLogsAndDailyWaterRecord
import com.example.myapplication.data.models.ReminderTimings
import com.example.myapplication.utils.DateString
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDatabaseDao {

    @Query("SELECT * FROM daily_water_record WHERE date = :date")
    fun getDailyWaterRecord(date:String = DateString.getTodaysDate()):Flow<DailyWaterRecord>

    @Query("SELECT * FROM daily_water_record WHERE date BETWEEN :startDate AND :endDate")
    fun getDailyWaterRecordsList(endDate:String = DateString.getTodaysDate(), startDate:String):Flow<List<DailyWaterRecord>>

    @Query("SELECT * FROM drink_logs WHERE date = :date")
    fun getDrinkLogs(date:String = DateString.getTodaysDate()): Flow<List<DrinkLogs>>

    @Query("SELECT * FROM reminder_timings")
    fun getReminderTimings(): Flow<List<ReminderTimings>>

//    @Query("SELECT * FROM daily_water_record ORDER BY date ASC LIMIT 1")
//    fun getFirstWaterRecord(): Flow<DailyWaterRecord>


//    @Query("SELECT curr_water_amount FROM daily_water_record WHERE date = :date")
//    fun getTodaysCurrWaterAmount(date:String = DateString.getTodaysDate()):Flow<Int>
//
//    @Query("SELECT is_goal_achieved FROM daily_water_record WHERE date = :date")
//    fun getTodaysIsGoalAchieved(date:String = DateString.getTodaysDate()):Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWaterRecord(dailyWaterRecord: DailyWaterRecord):Long
    //Long Return value is the row count of database where the object was inserted
    //If insertion failed then returns -1

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinkLog(drinkLog: DrinkLogs): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminderTiming(reminderTiming: ReminderTimings): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDailyWaterRecord(dailyWaterRecord: DailyWaterRecord)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDrinkLog(drinkLog: DrinkLogs)

    //Used only to update active status of the reminder
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateReminderTiming(reminderTiming: ReminderTimings)

    @Delete
    suspend fun deleteDrinkLog(drinkLog: DrinkLogs)

    @Delete
    suspend fun deleteReminderTiming(reminderTiming: ReminderTimings)

    @Query("SELECT COUNT(id) FROM drink_logs")
    suspend fun getDrinkLogsCount(): Int

    @Query("SELECT COUNT(date) FROM daily_water_record")
    suspend fun getWaterRecordsCount(): Int

    @Query("SELECT COUNT(date) FROM daily_water_record WHERE is_goal_achieved = 1")
    suspend fun getCompletedWaterRecordsCount(): Int
}
