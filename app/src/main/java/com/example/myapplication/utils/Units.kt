package com.example.myapplication.utils

class Units {
  companion object{
    const val KG = "kg"
    const val LB = "lb"
    const val ML = "ml"
    const val OZ = "oz"

    fun convertOzToMl(value:Int):Int {
      return (value*29.6f).toInt()
    }
  }
}