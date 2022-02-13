package com.example.myapplication.utils

import kotlin.math.ceil

object Weight {
  const val NOT_SET = -1
  const val DEFAULT_WEIGHT_KG = 70
  const val MAX_WEIGHT = 600
  const val MIN_WEIGHT = 10

  fun kgToLb(weight:Int): Int {
    return ceil(weight*2.2).toInt()
  }

  fun lbToKg(weight:Int): Int {
    return ceil(weight*0.4536).toInt()
  }

}