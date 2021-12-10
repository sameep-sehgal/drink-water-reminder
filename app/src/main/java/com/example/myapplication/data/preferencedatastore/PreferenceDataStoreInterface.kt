package com.example.myapplication.data.preferencedatastore

import kotlinx.coroutines.flow.Flow


//Set of key value pairs to store small chunks of data
interface PreferenceDataStoreInterface {
    fun recommendedWaterIntake() :Flow<Int>
    suspend fun setRecommendedWaterIntake(amount:Int)
    fun gender() :Flow<String>
    suspend fun setGender(gender:String)
    fun weight() :Flow<Int>
    suspend fun setWeight(amount:Int)
}