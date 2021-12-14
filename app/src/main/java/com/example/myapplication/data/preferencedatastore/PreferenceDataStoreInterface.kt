package com.example.myapplication.data.preferencedatastore

import kotlinx.coroutines.flow.Flow


//Set of key value pairs to store small chunks of data
interface PreferenceDataStoreInterface {
    fun isUserInfoCollected() :Flow<Boolean>
    suspend fun setIsUserInfoCollected(value:Boolean)

    //Personal Settings
    fun units() :Flow<String>
    suspend fun setUnits(units:String)
    fun gender() :Flow<String>
    suspend fun setGender(gender:String)
    fun weight() :Flow<Int>
    suspend fun setWeight(amount:Int)
    fun recommendedWaterIntake() :Flow<Int>
    suspend fun setRecommendedWaterIntake(amount:Int)
    fun dailyWaterGoal() :Flow<Int>
    suspend fun setDailyWaterGoal(amount:Int)

    //Reminder Settings
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

    //Main Settings
    fun appTheme() :Flow<String>
    suspend fun setAppTheme(theme:String)
}