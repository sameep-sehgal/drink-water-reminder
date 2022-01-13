package com.example.myapplication.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.screens.collectuserdata.GetReminderPeriod
import com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent.SetGenderSettingDialog

class Settings {
  companion object {
    const val REMINDER_PERIOD = "Reminder Period"
    const val REMINDER_FREQUENCY = "Reminder Frequency"
    const val REMINDER_SOUND = "Reminder Sound"
    const val REMINDER_AFTER_GOAL_ACHEIVED = "Remind after goal is achieved?"
    const val GENDER = "Gender"
    const val WEIGHT = "Weight"
    const val RECOMMENDED_WATER_INTAKE = "Recommended Water Intake"
    const val DAILY_WATER_GOAL = "Daily Water Goal"
    const val APP_THEME = "App Theme"
    const val RESET_DATA = "Reset Data"
    const val RATE_US = "Rate Us"
    const val SHARE = "Share"
    const val CONTACT_DEVELOPERS = "Contact Developers"
    const val GLASS_CAPACITY = "Glass Capacity"
    const val MUG_CAPACITY = "Mug Capacity"
    const val BOTTLE_CAPACITY = "Bottle Capacity"
  }
}