package com.example.myapplication.utils

import kotlin.math.ceil

class Weight {
  companion object{
    const val NOT_SET = -1
    const val DEFAULT_MALE_WEIGHT_KG = 70
    const val DEFAULT_MALE_WEIGHT_LB = 154
    const val DEFAULT_FEMALE_WEIGHT_KG = 60
    const val DEFAULT_FEMALE_WEIGHT_LB = 132
    const val MAX_WEIGHT = 600
    const val MIN_WEIGHT = 10

    fun kgToLb(weight:Int): Int {
      return ceil(weight*2.2).toInt()
    }

    fun lbToKg(weight:Int): Int {
      return ceil(weight*0.4536).toInt()
    }
  }
}