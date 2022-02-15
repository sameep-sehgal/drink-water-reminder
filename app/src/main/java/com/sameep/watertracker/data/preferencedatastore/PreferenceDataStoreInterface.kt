package com.sameep.watertracker.data.preferencedatastore

import kotlinx.coroutines.flow.Flow


//Set of key value pairs to store small chunks of data
interface PreferenceDataStoreInterface {
  fun isUserInfoCollected() :Flow<Boolean>
  suspend fun setIsUserInfoCollected(value:Boolean)
  fun firstWaterDataDate() :Flow<String>
  suspend fun setFirstWaterDataDate(date:String)
  fun beverage() :Flow<String>
  suspend fun setBeverage(selectedBeverage:String)

  //Personal Settings
  fun weightUnit() :Flow<String>
  suspend fun setWeightUnit(unit:String)
  fun waterUnit() :Flow<String>
  suspend fun setWaterUnit(unit:String)
  fun gender() :Flow<String>
  suspend fun setGender(gender:String)
  fun weight() :Flow<Int>
  suspend fun setWeight(amount:Int)
  fun activityLevel() :Flow<String>
  suspend fun setActivityLevel(activityLevel:String)
  fun weather() :Flow<String>
  suspend fun setWeather(weather: String)

  //How Much Water To Drink
  fun isDailyWaterGoalTrackingRecommendedIntake() :Flow<Boolean>
  suspend fun setIsDailyWaterGoalTrackingRecommendedIntake(value:Boolean)
  fun recommendedWaterIntake() :Flow<Int>
  suspend fun setRecommendedWaterIntake(amount:Int)
  fun dailyWaterGoal() :Flow<Int>
  suspend fun setDailyWaterGoal(amount:Int)

  //Reminder Settings
  fun isReminderOn() :Flow<Boolean>
  suspend fun setIsReminderOn(value:Boolean)
  fun reminderPeriodStart() :Flow<String>
  suspend fun setReminderPeriodStart(time:String)
  fun reminderPeriodEnd() :Flow<String>
  suspend fun setReminderPeriodEnd(time:String)
  fun reminderGap() :Flow<Int>
  suspend fun setReminderGap(gapTime:Int)
  fun reminderAfterGoalAchieved() :Flow<Boolean>
  suspend fun setReminderAfterGoalAchieved(value:Boolean)
  fun reminderSound() :Flow<String>
  suspend fun setReminderSound(sound:String)

  //Container Settings
  fun glassCapacity() :Flow<Int>
  suspend fun setGlassCapacity(capacity:Int)
  fun mugCapacity() :Flow<Int>
  suspend fun setMugCapacity(capacity:Int)
  fun bottleCapacity() :Flow<Int>
  suspend fun setBottleCapacity(capacity:Int)
}