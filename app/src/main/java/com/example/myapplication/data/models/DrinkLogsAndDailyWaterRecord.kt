package com.example.myapplication.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class DrinkLogsAndDailyWaterRecord(
    @Embedded
    val dailyWaterRecord: DailyWaterRecord,
    @Relation(
        parentColumn = "date",
        entityColumn = "date"
    )
    val drinkLogs: List<DrinkLogs>
)
