package com.example.myapplication.utils

class RecommendedWaterIntake {
  companion object{
    const val NOT_SET = -1
    const val MAX_WATER_LEVEL_IN_ML = 5000
    const val MIN_WATER_LEVEL_IN_ML = 800
    const val MAX_WATER_LEVEL_IN_OZ = 27
    const val MIN_WATER_LEVEL_IN_OZ = 169
    private const val PER_HOUR_ACTIVITY_WATER_INCREASE_IN_OZ = 24
    private const val PER_HOUR_ACTIVITY_WATER_INCREASE_IN_ML = 710

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