package com.example.myapplication.utils

class RecommendedWaterIntake {
  companion object{
    const val NOT_SET = -1
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
      return waterIntakeInMl + (10 - (waterIntakeInMl%10))  //Round to nearest 10s
    }

    fun addForActivity(
      activityMinutes:Int,
      waterUnit:String
    ):Int {
      if(waterUnit == Units.OZ)
        return ((activityMinutes/60f) * PER_HOUR_ACTIVITY_WATER_INCREASE_IN_OZ).toInt()

      return ((activityMinutes/60f) * PER_HOUR_ACTIVITY_WATER_INCREASE_IN_ML).toInt()
    }

    fun addForWeather(
      weather: String,
      baseWaterIntake:Int
    ):Int {
      if(weather == Weather.HOT)
        return (0.15f*baseWaterIntake).toInt()

      if(weather == Weather.WARM)
        return (0.075f*baseWaterIntake).toInt()

      if(weather == Weather.COLD)
        return ((-0.075f)*baseWaterIntake).toInt()

      return 0
    }
  }
}