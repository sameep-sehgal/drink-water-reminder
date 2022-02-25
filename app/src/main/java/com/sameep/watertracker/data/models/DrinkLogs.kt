package com.sameep.watertracker.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.sameep.watertracker.utils.Beverages
import com.sameep.watertracker.utils.DateString
import java.util.*

@Entity(tableName = "drink_logs",
    foreignKeys = [ForeignKey(
        entity = DailyWaterRecord::class,
        parentColumns = arrayOf("date"),
        childColumns = arrayOf("date"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class DrinkLogs(
  @PrimaryKey(autoGenerate = true) var id:Int = 0,
  var time:Long = Date().time,
  var amount:Int,
  var icon:Int,
  @ColumnInfo(index = true)
  var date:String = DateString.getTodaysDate(),
  @ColumnInfo(defaultValue = Beverages.DEFAULT)
    var beverage:String
)
