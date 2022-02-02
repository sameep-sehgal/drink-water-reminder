package com.example.myapplication.data.roomdatabase

import android.content.Context
import androidx.room.*
import com.example.myapplication.data.models.Beverage
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs

@Database(
    entities = [DrinkLogs::class, DailyWaterRecord::class, Beverage::class],
    version = 4,
    autoMigrations = [
        AutoMigration (
            from = 3,
            to = 4
        )
    ]
)
abstract class WaterDatabase: RoomDatabase() {
    abstract fun waterDatabaseDao() : WaterDatabaseDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: WaterDatabase? = null

        fun getInstance(context: Context): WaterDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, WaterDatabase::class.java, "water_db")
                .build()
    }
}


//@Database(
//    entities = [DrinkLogs::class, DailyWaterRecord::class],
//    version = 1,
//)
//abstract class WaterDatabase: RoomDatabase() {
//    abstract fun waterDatabaseDao() : WaterDatabaseDao
//}