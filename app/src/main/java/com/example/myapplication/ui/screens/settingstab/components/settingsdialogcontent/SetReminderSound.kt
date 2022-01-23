package com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.remindernotification.ReminderReceiver
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.utils.ReminderSound
import com.example.myapplication.utils.Settings
import java.util.*

@Composable
fun SetReminderSoundSettingDialog(
  reminderSound: String,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowDialog:(Boolean) -> Unit,
  reminderPeriodStart: String,
  reminderPeriodEnd: String,
  glassCapacity:Int,
  mugCapacity:Int,
  bottleCapacity:Int,
  reminderGap: Int,
  dailyWaterGoal: Int,
  remindAfterGoalAchieved: Boolean,
  waterUnit: String,
  context:Context
) {
  val selectedReminderSound = remember { mutableStateOf(reminderSound) }
  val playAudio = remember { mutableStateOf(false) }
  val setPlayAudio = {value:Boolean -> playAudio.value = value}

  ShowDialog(
    title = "Set ${Settings.REMINDER_SOUND}",
    content = {
      //Bring Show Dialog here
      Column {
        if(playAudio.value){
          PlayAudio(
            reminderSound = selectedReminderSound.value,
            setPlayAudio
          )
        }
        ReminderSound.LIST_OF_SOUNDS.forEach { sound ->
          Row(
            modifier = Modifier
              .clickable {
                selectedReminderSound.value = sound
                setPlayAudio(true)
              }
              .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Checkbox(
              checked = selectedReminderSound.value == sound,
              onCheckedChange = {
                selectedReminderSound.value = sound
                setPlayAudio(true)
              }
            )
            Text(
              text = sound,
              style = Typography.subtitle1
            )
          }
        }
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {
      preferenceDataStoreViewModel.setReminderSound(selectedReminderSound.value)
      val calendar = Calendar.getInstance()
      calendar.add(Calendar.MILLISECOND, reminderGap)
      //TODO(Delete old channel and create new channel)
      ReminderReceiver.setReminder(
        time = calendar.timeInMillis,
        reminderPeriodStart = reminderPeriodStart,
        reminderPeriodEnd = reminderPeriodEnd,
        reminderGap = reminderGap,
        glassCapacity = glassCapacity,
        mugCapacity = mugCapacity,
        bottleCapacity = bottleCapacity,
        channelId = selectedReminderSound.value,
        waterUnit = waterUnit,
        dailyWaterGoal = dailyWaterGoal,
        remindAfterGoalAchieved = remindAfterGoalAchieved,
        context = context
      )
    }
  )
}

@Composable
fun PlayAudio(
  reminderSound: String,
  setPlayAudio: (Boolean) -> Unit
) {
  val context = LocalContext.current
  val mediaPlayerCompletionListener = MediaPlayer.OnCompletionListener {
    setPlayAudio(false)
  }

  val mediaPlayer: MediaPlayer? = if(reminderSound == ReminderSound.DEVICE_DEFAULT){
    MediaPlayer.create(
      context,
      RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    )
  }else {
    ReminderSound.NAME_VALUE_MAPPER[reminderSound]?.let { MediaPlayer.create(context, it) }
  }
  mediaPlayer?.setOnCompletionListener(mediaPlayerCompletionListener)

  DisposableEffect(Unit){
    mediaPlayer?.start()

    onDispose {
      Log.d("TAG", "PlayAudio: Stop Release")
      mediaPlayer?.stop()
      mediaPlayer?.release()
    }
  }
}