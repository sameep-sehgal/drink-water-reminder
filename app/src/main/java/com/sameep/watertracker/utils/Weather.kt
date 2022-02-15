package com.sameep.watertracker.utils

object Weather{
  private const val HOT = "Hot"
  private const val WARM = "Warm"
  const val NORMAL = "Normal"
  private const val COLD = "Cold"

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