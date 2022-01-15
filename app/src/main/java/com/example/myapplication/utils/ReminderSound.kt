package com.example.myapplication.utils

import com.example.myapplication.R

class ReminderSound {
  companion object{
    const val WATER_DROP = "Water Drop"
    const val WATER_BLOOP = "Water Bloop"
    const val RIVER_FLOW = "River Flow"
    const val POURING_WATER = "Pouring Water"
    const val POURING_ICEWATER = "Pouring Icewater"
    const val DEVICE_DEFAULT = "Device Default"

    val NAME_VALUE_MAPPER = hashMapOf(
      WATER_DROP to R.raw.water_drop,
      WATER_BLOOP to R.raw.water_bloop,
      RIVER_FLOW to R.raw.river_flow,
      POURING_WATER to R.raw.pouring_water,
      POURING_ICEWATER to R.raw.pouring_icewater
    )
    val LIST_OF_SOUNDS = listOf(
      WATER_DROP,
      WATER_BLOOP,
      RIVER_FLOW,
      POURING_WATER,
      POURING_ICEWATER,
      DEVICE_DEFAULT
    )
  }
}