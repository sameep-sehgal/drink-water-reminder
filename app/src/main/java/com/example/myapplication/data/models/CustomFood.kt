package com.example.myapplication.data.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class CustomFood(
  @PrimaryKey
  @ColumnInfo(name = "name")
  var name: String,
  @ColumnInfo(name = "water_content")
  var waterContent: Int,//value in percent
  @ColumnInfo(name = "icon")
  var icon: Int
)
