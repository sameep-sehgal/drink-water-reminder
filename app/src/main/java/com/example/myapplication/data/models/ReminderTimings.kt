package com.example.myapplication.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder_timings")
data class ReminderTimings(
  @PrimaryKey
  var time: String, //Format -> "hh:mm"
  @ColumnInfo(name = "active")
  var active: Boolean = true
)
