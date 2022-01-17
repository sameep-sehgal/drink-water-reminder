package com.example.myapplication.ui.screens.hometab.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun SetTodaysReminderButton(modifier: Modifier, setShowWorkoutDialog:(Boolean)->Unit){
  var todaysReminder by remember{ mutableStateOf(false) }
  val context = LocalContext.current
  fun value(): String {
    return if(todaysReminder) "ON" else "OFF"
  }

  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = painterResource(R.drawable.notificationbell),
      contentDescription = "Workout Button",
      modifier = Modifier
        .clip(CircleShape)
        .clickable(
          enabled = true,
          onClick = {
            todaysReminder = !todaysReminder
            Toast.makeText(
              context,
              "Reminders turned " + value() + " for today.",
              Toast.LENGTH_SHORT
            ).show()
          }
        )
        .alpha(if (todaysReminder) 1.0f else 0.35f)
    )
    if(!todaysReminder){
      Row(
        modifier = Modifier.size(48.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
      ) {
        Image(
          painter = painterResource(id = R.drawable.cancel_icon),
          contentDescription = "Cancel Icon",
          alignment = Alignment.BottomEnd,
          modifier = Modifier.size(20.dp).alpha(0.8f)
        )
      }
    }
  }
}