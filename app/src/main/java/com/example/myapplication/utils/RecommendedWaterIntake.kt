package com.example.myapplication.utils

class RecommendedWaterIntake {
  companion object{
    const val NOT_SET = -1
    const val MAX_WATER_LEVEL_IN_ML = 5000
    const val MIN_WATER_LEVEL_IN_ML = 800
    const val MIN_WATER_LEVEL_IN_ML_FOR_LOG = 50
    const val MAX_WATER_LEVEL_IN_ML_FOR_LOG = 1000
    const val MIN_WATER_LEVEL_IN_OZ_FOR_LOG = 1
    const val MAX_WATER_LEVEL_IN_OZ_FOR_LOG = 35
    const val MAX_WATER_LEVEL_IN_OZ = 169
    const val MIN_WATER_LEVEL_IN_OZ = 27
    const val DEFAULT_WATER_GOAL_IN_ML = 2500
    const val DEFAULT_WATER_GOAL_IN_OZ = 85
    const val DEFAULT_CUSTOM_ADD_WATER_CONTAINTER = Container.MUG
    private const val DEFAULT_CUSTOM_ADD_WATER_VALUE_IN_ML = 300
    private const val DEFAULT_CUSTOM_ADD_WATER_VALUE_IN_OZ = 10
    private const val PER_HOUR_ACTIVITY_WATER_INCREASE_IN_OZ = 24
    private const val PER_HOUR_ACTIVITY_WATER_INCREASE_IN_ML = 710

    val valuesForWaterGoalNumberPicker = { waterUnit:String ->
      val res: MutableList<String> = mutableListOf()
      if(waterUnit == Units.ML){
        for(i in MIN_WATER_LEVEL_IN_ML/10 .. MAX_WATER_LEVEL_IN_ML/10){
          res.add((i*10).toString())
        }
      }else{
        for(i in MIN_WATER_LEVEL_IN_OZ .. MAX_WATER_LEVEL_IN_OZ){
          res.add(i.toString())
        }
      }

      res
    }

    val valuesForWaterLogNumberPicker = { waterUnit:String ->
      val res: MutableList<String> = mutableListOf()
      if(waterUnit == Units.ML){
        for(i in MIN_WATER_LEVEL_IN_ML_FOR_LOG/10..MAX_WATER_LEVEL_IN_ML_FOR_LOG/10){
          res.add((i*10).toString())
        }
      }else{
        for(i in MIN_WATER_LEVEL_IN_OZ_FOR_LOG..MAX_WATER_LEVEL_IN_OZ_FOR_LOG){
          res.add(i.toString())
        }
      }

      res
    }

    fun defaultCustomAddWaterIntake(waterUnit: String): Int{
      if(waterUnit == Units.ML){
        return DEFAULT_CUSTOM_ADD_WATER_VALUE_IN_ML
      }
      return DEFAULT_CUSTOM_ADD_WATER_VALUE_IN_OZ
    }

    fun calculateRecommendedWaterIntake(
      gender:String,
      weight:Int,
      weightUnit:String,
      waterUnit:String,
      activityLevel: String,
      weather: String
    ):Int{
      val weightInLb = if(weightUnit == Units.KG) Weight.kgToLb(weight) else weight
      var waterIntakeInOz: Int

      if(gender == Gender.MALE){
        waterIntakeInOz = (0.5f * weightInLb).toInt()
      }else{
        waterIntakeInOz = (0.45f * weightInLb).toInt()
      }
      val activityAmount = addForActivity(activityLevel,waterIntakeInOz)
      val weatherAmount = addForWeather(weather,waterIntakeInOz)

      waterIntakeInOz += activityAmount
      waterIntakeInOz += weatherAmount

      if(waterUnit == Units.OZ)
        return waterIntakeInOz

      val waterIntakeInMl = Units.convertOzToMl(waterIntakeInOz)
      return roundTo10(waterIntakeInMl)
    }

    private fun addForActivity(
      activityLevel: String,
      baseWaterIntake: Int
    ):Int {
      var res = 0

      (ActivityLevel.PERCENTAGE_CHANGE_MAPPER[activityLevel]?.times(baseWaterIntake))?.let{
        res = it.toInt()
      }

      return res
    }

    private fun addForWeather(
      weather: String,
      baseWaterIntake:Int
    ):Int {
      var res = 0

      (Weather.PERCENTAGE_CHANGE_MAPPER[weather]?.times(baseWaterIntake))?.let{
        res = it.toInt()
      }

      return res
    }

    fun roundTo10(num:Int) :Int{
      if(num%10 == 0) return num
      return num + (10 - (num%10)) //Round to nearest 10s
    }
  }
}