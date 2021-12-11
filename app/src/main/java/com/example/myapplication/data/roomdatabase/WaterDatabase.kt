package com.example.myapplication.data.roomdatabase

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.data.models.ReminderTimings

@Database(
    entities = [DrinkLogs::class, DailyWaterRecord::class, ReminderTimings::class],
    version = 2,
    autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]
)
abstract class WaterDatabase: RoomDatabase() {
    abstract fun waterDatabaseDao() : WaterDatabaseDao
}


//@Database(
//    entities = [DrinkLogs::class, DailyWaterRecord::class],
//    version = 1,
//)
//abstract class WaterDatabase: RoomDatabase() {
//    abstract fun waterDatabaseDao() : WaterDatabaseDao
//}