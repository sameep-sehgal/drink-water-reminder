package com.example.myapplication.repository

import com.example.myapplication.data.models.ReminderTimings
import com.example.myapplication.data.preferencedatastore.PreferenceDataStore
import com.example.myapplication.data.roomdatabase.WaterDatabaseDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepository @Inject constructor(
  private val waterDatabaseDao: WaterDatabaseDao,
  private val preferenceDataStore: PreferenceDataStore
) {

  fun getReminderTimings(): Flow<List<ReminderTimings>> {
    return waterDatabaseDao.getReminderTimings()
  }

  suspend fun updateReminderTiming(reminderTiming: ReminderTimings)
    = waterDatabaseDao.updateReminderTiming(reminderTiming)

  suspend fun deleteReminderTiming(reminderTiming: ReminderTimings)
    = waterDatabaseDao.deleteReminderTiming(reminderTiming)

  suspend fun insertReminderTiming(reminderTiming: ReminderTimings)
    = waterDatabaseDao.insertReminderTiming(reminderTiming)

}