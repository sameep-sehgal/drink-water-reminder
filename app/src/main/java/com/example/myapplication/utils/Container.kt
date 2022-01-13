package com.example.myapplication.utils

import com.example.myapplication.R

class Container {
  companion object {
    const val BOTTLE = 0
    const val MUG = 1
    const val GLASS = 2

    private const val BASE_GLASS_CAPACITY_ML = 200
    private const val BASE_MUG_CAPACITY_ML = 300
    private const val BASE_BOTTLE_CAPACITY_ML = 500

    private const val BASE_GLASS_CAPACITY_OZ = 7
    private const val BASE_MUG_CAPACITY_OZ = 10
    private const val BASE_BOTTLE_CAPACITY_OZ = 15

    //TODO("Add more containers here")
    val IMAGE_MAPPER = hashMapOf(
      BOTTLE to R.drawable.bottle_4,
      MUG to R.drawable.mug_3,
      GLASS to R.drawable.glass_2
    )

    val OPTIONS_FOR_DROPDOWN = listOf(
      hashMapOf<String,Any?>("text" to "Bottle", "value" to BOTTLE),
      hashMapOf<String,Any?>("text" to "Mug", "value" to MUG),
      hashMapOf<String,Any?>("text" to "Glass", "value" to GLASS),
    )

    fun getSelectedOptionForDropdown(icon:Int): HashMap<String,Any?> {
      var res: HashMap<String,Any?> = hashMapOf()
      when(icon) {
        BOTTLE -> res = hashMapOf("text" to "Bottle", "value" to BOTTLE)
        MUG -> res = hashMapOf("text" to "Mug   ", "value" to MUG)
        GLASS -> res = hashMapOf("text" to "Glass ", "value" to GLASS)
      }
      return res
    }

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
}