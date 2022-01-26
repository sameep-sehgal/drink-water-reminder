package com.example.myapplication.ui.screens.collectuserdata.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.remindernotification.ReminderReceiver
import com.example.myapplication.utils.*
import java.util.*

@Composable
fun CalculateWaterIntake(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  currentScreen:Int,
  setCurrentScreen:(Int) -> Unit
) {
  val context = LocalContext.current

  val gender = preferenceDataStoreViewModel.gender.collectAsState(initial = Gender.NOT_SET)
  val weight = preferenceDataStoreViewModel.weight.collectAsState(initial = Weight.NOT_SET)
  val weightUnit = preferenceDataStoreViewModel.weightUnit.collectAsState(initial = Units.KG)
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.ML)
  val reminderPeriodStart = preferenceDataStoreViewModel.reminderPeriodStart.collectAsState(initial = ReminderPeriod.NOT_SET)
  val reminderPeriodEnd = preferenceDataStoreViewModel.reminderPeriodEnd.collectAsState(initial = ReminderPeriod.NOT_SET)
  val reminderGap = preferenceDataStoreViewModel.reminderGap.collectAsState(initial = ReminderGap.NOT_SET)
  val recommendedWaterIntake =
    RecommendedWaterIntake.calculateBaseWaterIntake(
      gender = gender.value,
      weight = weight.value,
      weightUnit = weightUnit.value,
      waterUnit = waterUnit.value
    )

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        modifier = Modifier.padding(0.dp, 16.dp),
        text = "Your Hydration Plan",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
      )

      Text(
        modifier = Modifier.padding(0.dp, 16.dp),
        text = "Your Recommended Water Intake is",
        textAlign = TextAlign.Center,
        fontSize = 18.sp
      )

      Text(
        modifier = Modifier.padding(0.dp, 16.dp),
        text = "$recommendedWaterIntake${waterUnit.value}",
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary,
        textAlign = TextAlign.Center
      )

      Text(
        modifier = Modifier.padding(0.dp, 16.dp),
        text = "Try to divide your water intake in 8 - 12 servings throughout the day",
        textAlign = TextAlign.Center,
        fontSize = 16.sp
      )

      Text(
        modifier = Modifier.padding(0.dp, 16.dp),
        text = "We will remind you to drink water between ${reminderPeriodStart.value} and ${reminderPeriodEnd.value} at ${ReminderGap.GAP_OPTION_TEXT_MAPPER[reminderGap.value]}",
        textAlign = TextAlign.Center,
        fontSize = 16.sp
      )
    }


    LinearProgressIndicator(currentScreen/6f)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      Button(
        onClick = {
          setCurrentScreen(currentScreen - 1)
        },
        shape = CircleShape
      ) {
        Text(
          text = "Back",
          fontWeight = FontWeight.ExtraBold,
          fontSize = 21.sp
        )
      }
      Button(
        onClick = {
          preferenceDataStoreViewModel.setRecommendedWaterIntake(recommendedWaterIntake)
          preferenceDataStoreViewModel.setDailyWaterGoal(recommendedWaterIntake)
          preferenceDataStoreViewModel.setReminderSound(ReminderSound.DEFAULT_CHANNEL_ID)

          val baseGlassCapacity = Container.baseGlassCapacity(waterUnit.value)
          val baseMugCapacity = Container.baseMugCapacity(waterUnit.value)
          val baseBottleCapacity = Container.baseBottleCapacity(waterUnit.value)
          //Set Repeating Reminder
          val calendar = Calendar.getInstance()
          calendar.add(Calendar.MILLISECOND, reminderGap.value)
          ReminderReceiver.setReminder(
            calendar.timeInMillis,
            reminderPeriodStart.value,
            reminderPeriodEnd.value,
            reminderGap.value,
            baseGlassCapacity,
            baseMugCapacity,
            baseBottleCapacity,
            ReminderSound.DEFAULT_CHANNEL_ID,
            waterUnit.value,
            recommendedWaterIntake ,
            true,
            context
          )

          preferenceDataStoreViewModel.setGlassCapacity(baseGlassCapacity)
          preferenceDataStoreViewModel.setMugCapacity(baseMugCapacity)
          preferenceDataStoreViewModel.setBottleCapacity(baseBottleCapacity)

          //User Data stored now switch to TabLayout HomeScreen
          preferenceDataStoreViewModel.setIsUserInfoCollected(true)
        },
        shape = CircleShape
      ) {
        Text(
          fontSize = 21.sp,
          text = "Let's Go",
          fontWeight = FontWeight.ExtraBold
        )
      }
    }
  }
}