package com.example.myapplication.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.utils.DateString
import java.util.*

@Entity(tableName = "daily_water_record")
data class DailyWaterRecord(
    @PrimaryKey var date: String = DateString.getTodaysDate(),
    var goal: Int,
    @ColumnInfo(name = "curr_water_amount")
    var currWaterAmount: Int = 0,
    @ColumnInfo(name = "is_goal_achieved")
    var isGoalAchieved:Boolean = goal <= currWaterAmount
    )
