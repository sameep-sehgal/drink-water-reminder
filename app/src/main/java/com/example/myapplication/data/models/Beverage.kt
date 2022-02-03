package com.example.myapplication.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beverages")
data class Beverage(
  @PrimaryKey
  @ColumnInfo(name = "name")
  var name: String,
  @ColumnInfo(name = "icon")
  var icon: Int,
  @ColumnInfo(name = "importance", defaultValue = 0.toString())
  var importance:Int = 0
)
