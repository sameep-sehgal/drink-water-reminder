package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowBooleanValue
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowSelectValue
import com.example.myapplication.ui.screens.settingstab.components.SettingsSubheading
import com.example.myapplication.ui.theme.Typography

val horizontalPaddingSettings = 8.dp
val verticalPaddingSettings = 16.dp

@Composable
fun SettingsTab():Unit{
  val scrollState = rememberScrollState()
  Column(
    modifier = Modifier.verticalScroll(scrollState)
  ) {
    ReminderSettings()
    PersonalSettings()
    MainSettings()
  }
}

@Composable
fun ReminderSettings(){
  SettingsSubheading(text = "Reminder Settings")
  Divider()
  Column {
    SettingsRowSelectValue(
      text = "Reminder Period",
      value = "08:00-22:00",
      onSettingsRowClick = { /*TODO()*/ }
    )
    SettingsRowSelectValue(
      text = "Reminder Frequency",
      value = "Every 1 hour",
      onSettingsRowClick = { /*TODO()*/ }
    )
    SettingsRowBooleanValue(
      text = "Remind after goal is achieved?",
      value = true,
      onCheckedChange = {}
    )
    SettingsRowSelectValue(
      text = "Reminder Sound",
      value = "Selected Value",
      onSettingsRowClick = { /*TODO()*/ }
    )
  }
  Divider()
}

@Composable
fun PersonalSettings(){
  SettingsSubheading(text = "Personal Settings")
  Divider()
  Column {
    SettingsRowSelectValue(
      text = "Gender",
      value = "Male",
      onSettingsRowClick = { /*TODO()*/ }
    )
    SettingsRowSelectValue(
      text = "Weight",
      value = "70kg",
      onSettingsRowClick = { /*TODO()*/ }
    )
    SettingsRowSelectValue(
      text = "Units",
      value = "kg/ml",
      onSettingsRowClick = { /*TODO()*/ }
    )
    SettingsRowSelectValue(
      text = "Recommended Water Intake",
      value = "2500ml",
      onSettingsRowClick = { /*TODO()*/ }
    )
    SettingsRowSelectValue(
      text = "Daily Water Goal",
      value = "2700ml",
      onSettingsRowClick = { /*TODO()*/ }
    )
  }
  Divider()
}

@Composable
fun MainSettings(){
  SettingsSubheading(text = "Main Settings")
  Divider()
  SettingsRowSelectValue(
    text = "App Theme",
    value = "Default",
    onSettingsRowClick = { /*TODO()*/ }
  )
  Row(
    modifier = Modifier.padding(horizontal = horizontalPaddingSettings, vertical = verticalPaddingSettings)
  ) {
    Text(text = "Reset Data", style = Typography.subtitle1)
  }
  Row(
    modifier = Modifier.padding(horizontal = horizontalPaddingSettings, vertical = verticalPaddingSettings)
  ) {
    Text(text = "Rate Us", style = Typography.subtitle1)
  }
  Row(
    modifier = Modifier.padding(horizontal = horizontalPaddingSettings, vertical = verticalPaddingSettings)
  ) {
    Text(text = "Share", style = Typography.subtitle1)
  }
  Row(
    modifier = Modifier.padding(horizontal = horizontalPaddingSettings, vertical = verticalPaddingSettings)
  ) {
    Text(text = "Contact Developers", style = Typography.subtitle1)
  }
  Divider()
}