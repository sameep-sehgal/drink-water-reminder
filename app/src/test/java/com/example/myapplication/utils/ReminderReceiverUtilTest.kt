package com.example.myapplication.utils

import com.example.myapplication.data.models.DailyWaterRecord
import org.junit.Assert.*
import org.junit.Test
import org.junit.After
import org.junit.Before
import java.util.*

class ReminderReceiverUtilTest {

  private lateinit var currTime: Calendar
  private lateinit var startTime: String
  private lateinit var endTime: String
  private var remindAfterGoalAchieved: Boolean = false
  private lateinit var todaysWaterRecord: DailyWaterRecord

  @Before
  fun setUp() {
    currTime = Calendar.getInstance()
    startTime = ReminderPeriod.DEFAULT_REMINDER_PERIOD_START
    endTime = ReminderPeriod.DEFAULT_REMINDER_PERIOD_END
    remindAfterGoalAchieved = false
    todaysWaterRecord = DailyWaterRecord(goal = 2500)
  }

  @After
  fun tearDown() {
  }

  /*
  * currTime == "04:30"
  * startTime == "06:00"
  * endTime == "22:00"
   */
  @Test
  fun currTimeLessThanStartTimeAndBothStartAndEndTimeOnSameDay_returnsFalse() {
    currTime.set(Calendar.HOUR_OF_DAY, 4)
    currTime.set(Calendar.MINUTE, 30)

    val res = ReminderReceiverUtil.shallNotify(
      reminderPeriodStart = startTime,
      reminderPeriodEnd = endTime,
      remindAfterGoalAchieved = remindAfterGoalAchieved,
      todaysWaterRecord = todaysWaterRecord,
      currTime = currTime
    )

    assertFalse(res)
  }

  /*
  * currTime == "10:30"
  * startTime == "06:00"
  * endTime == "22:00"
   */
  @Test
  fun currTimeBetweenStartTimeAndEndTimeAndBothStartAndEndTimeOnSameDay_returnsTrue() {
    currTime.set(Calendar.HOUR_OF_DAY, 10)
    currTime.set(Calendar.MINUTE, 30)

    val res = ReminderReceiverUtil.shallNotify(
      reminderPeriodStart = startTime,
      reminderPeriodEnd = endTime,
      remindAfterGoalAchieved = remindAfterGoalAchieved,
      todaysWaterRecord = todaysWaterRecord,
      currTime = currTime
    )

    assertTrue(res)
  }

  /*
  * currTime == "23:30"
  * startTime == "06:00"
  * endTime == "22:00"
   */
  @Test
  fun currTimeGreaterThanEndTimeAndBothStartAndEndTimeOnSameDay_returnsFalse() {
    currTime.set(Calendar.HOUR_OF_DAY, 23)
    currTime.set(Calendar.MINUTE, 30)

    val res = ReminderReceiverUtil.shallNotify(
      reminderPeriodStart = startTime,
      reminderPeriodEnd = endTime,
      remindAfterGoalAchieved = remindAfterGoalAchieved,
      todaysWaterRecord = todaysWaterRecord,
      currTime = currTime
    )

    assertFalse(res)
  }

  /*
  * currTime == "10:30"
  * startTime == "06:00"
  * endTime == "02:00" next day
   */
  @Test
  fun currTimeBetweenStartTimeAndEndTimeAndEndTimeOnNextDay_returnsTrue() {
    currTime.set(Calendar.HOUR_OF_DAY, 10)
    currTime.set(Calendar.MINUTE, 30)
    endTime = "02:00"

    val res = ReminderReceiverUtil.shallNotify(
      reminderPeriodStart = startTime,
      reminderPeriodEnd = endTime,
      remindAfterGoalAchieved = remindAfterGoalAchieved,
      todaysWaterRecord = todaysWaterRecord,
      currTime = currTime
    )

    assertTrue(res)
  }

  /*
  * currTime == "04:30"
  * startTime == "06:00"
  * endTime == "02:00" next day
   */
  @Test
  fun currTimeLessThanStartTimeAndEndTimeOnNextDay_returnsFalse() {
    currTime.set(Calendar.HOUR_OF_DAY, 4)
    currTime.set(Calendar.MINUTE, 30)
    endTime = "02:00"

    val res = ReminderReceiverUtil.shallNotify(
      reminderPeriodStart = startTime,
      reminderPeriodEnd = endTime,
      remindAfterGoalAchieved = remindAfterGoalAchieved,
      todaysWaterRecord = todaysWaterRecord,
      currTime = currTime
    )

    assertFalse(res)
  }

  /*
  * currTime == "01:00"
  * startTime == "06:00"
  * endTime == "02:00" next day
   */
  @Test
  fun currTimeBetweenStartTimeAndEndTimeAndStartTimeOnPreviousDate_returnsTrue() {
    currTime.set(Calendar.HOUR_OF_DAY, 10)
    currTime.set(Calendar.MINUTE, 30)
    endTime = "02:00"

    val res = ReminderReceiverUtil.shallNotify(
      reminderPeriodStart = startTime,
      reminderPeriodEnd = endTime,
      remindAfterGoalAchieved = remindAfterGoalAchieved,
      todaysWaterRecord = todaysWaterRecord,
      currTime = currTime
    )

    assertTrue(res)
  }
}