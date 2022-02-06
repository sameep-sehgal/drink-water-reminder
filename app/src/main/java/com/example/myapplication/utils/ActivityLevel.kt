package com.example.myapplication.utils

object ActivityLevel {
  const val VERY_ACTIVE = "Very Active"
  const val MODERATELY_ACTIVE = "Moderately Active"
  const val LIGHTLY_ACTIVE = "Lightly Active"
  const val SEDENTARY = "Sedentary"

  val PERCENTAGE_CHANGE_MAPPER = hashMapOf(
    VERY_ACTIVE to 0.25f,
    MODERATELY_ACTIVE to 0.15f,
    LIGHTLY_ACTIVE to 0.05f,
    SEDENTARY to 0f,
  )
}