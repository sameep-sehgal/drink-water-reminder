package com.example.myapplication.utils

import com.example.myapplication.R

object Container {
  const val BOTTLE = 0
  const val MUG = 1
  const val GLASS = 2

  private const val BASE_GLASS_CAPACITY_ML = 200
  private const val BASE_MUG_CAPACITY_ML = 300
  private const val BASE_BOTTLE_CAPACITY_ML = 500

  private const val BASE_GLASS_CAPACITY_OZ = 7
  private const val BASE_MUG_CAPACITY_OZ = 10
  private const val BASE_BOTTLE_CAPACITY_OZ = 15

  val IMAGE_MAPPER = hashMapOf(
    BOTTLE to R.drawable.bottle_4,
    MUG to R.drawable.mug_3,
    GLASS to R.drawable.glass_2
  )

  fun baseGlassCapacity(waterUnit:String): Int {
    if(waterUnit == Units.ML) return BASE_GLASS_CAPACITY_ML
    return BASE_GLASS_CAPACITY_OZ
  }

  fun baseMugCapacity(waterUnit:String): Int {
    if(waterUnit == Units.ML) return BASE_MUG_CAPACITY_ML
    return BASE_MUG_CAPACITY_OZ
  }

  fun baseBottleCapacity(waterUnit:String): Int {
    if(waterUnit == Units.ML) return BASE_BOTTLE_CAPACITY_ML
    return BASE_BOTTLE_CAPACITY_OZ
  }

}