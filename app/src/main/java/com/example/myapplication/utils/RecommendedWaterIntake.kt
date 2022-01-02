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

    val VALUES_FOR_WATER_GOAL_NUMBER_PICKER = { waterUnit:String ->
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

    val VALUES_FOR_WATER_LOG_NUMBER_PICKER = { waterUnit:String ->
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

    fun calculateBaseWaterIntake(
      gender:String,
      weight:Int,
      weightUnit:String,
      waterUnit:String,
    ):Int{
      val weightInLb = if(weightUnit == Units.KG) Weight.kgToLb(weight) else weight
      val waterIntakeInOz: Int

      if(gender == Gender.MALE){
        waterIntakeInOz = (0.5f * weightInLb).toInt()
      }else{
        waterIntakeInOz = (0.45f * weightInLb).toInt()
      }

      if(waterUnit == Units.OZ)
        return waterIntakeInOz

      val waterIntakeInMl = Units.convertOzToMl(waterIntakeInOz)
      return roundTo10(waterIntakeInMl)
    }

    fun addForActivity(
      activityMinutes:Int,
      waterUnit:String
    ):Int {
      var res = ((activityMinutes/60f) * PER_HOUR_ACTIVITY_WATER_INCREASE_IN_ML).toInt()
      if(waterUnit == Units.OZ)
        res = ((activityMinutes/60f) * PER_HOUR_ACTIVITY_WATER_INCREASE_IN_OZ).toInt()

      return roundTo10(res)
    }

    fun addForWeather(
      weather: String,
      baseWaterIntake:Int
    ):Int {
      var res = 0
      if(weather == Weather.HOT)
        res = (0.15f*baseWaterIntake).toInt()

      if(weather == Weather.WARM)
        res = (0.075f*baseWaterIntake).toInt()

      if(weather == Weather.COLD)
        res = ((-0.075f)*baseWaterIntake).toInt()

      return roundTo10(res)
    }

    fun roundTo10(num:Int) :Int{
      if(num%10 == 0) return num
      return num + (10 - (num%10)) //Round to nearest 10s
    }
  }
}