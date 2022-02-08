package com.example.myapplication.utils

object Weather{
  const val HOT = "Hot"
  const val WARM = "Warm"
  const val NORMAL = "Normal"
  const val COLD = "Cold"

  val PERCENTAGE_CHANGE_MAPPER = hashMapOf(
    HOT to 0.15f,
    WARM to 0.075f,
    NORMAL to 0f,
    COLD to -0.075f,
  )

  val OPTIONS = listOf(
    HOT,
    WARM,
    NORMAL,
    COLD
  )
}