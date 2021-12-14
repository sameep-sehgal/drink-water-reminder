package com.example.myapplication.utils

import com.example.myapplication.R

class ReminderSound {
  companion object{
    const val WATER_DROP = "Water Drop"
    const val WATER_BLOOP = "Water Bloop"
    const val RIVER_FLOW = "River Flow"
    const val POURING_WATER = "Pouring Water"
    const val POURING_ICEWATER = "Pouring Icewater"
    val NAME_VALUE_MAPPER = hashMapOf(
      "Water Drop" to R.raw.water_drop,
      "Water Bloop" to R.raw.water_bloop,
      "River Flow" to R.raw.river_flow,
      "Pouring Water" to R.raw.pouring_water,
      "Pouring Icewater" to R.raw.pouring_icewater
    )
  }
}