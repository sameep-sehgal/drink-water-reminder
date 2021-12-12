package com.example.myapplication.ui.screens.hometab.components.dialogs

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.data.models.ReminderTimings
import com.example.myapplication.ui.theme.Typography

@Composable
fun ReminderTimingsDialog(setShowReminderTimingsDialog: (Boolean)->Unit){
  Dialog(
    onDismissRequest = { setShowReminderTimingsDialog(false) }
  ) {
    Box(
      modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxHeight(0.85f)
        .padding(8.dp)
    ) {
      ReminderTimingsDialogContent()
    }
  }
}

@Composable
fun ReminderTimingsDialogContent(){
  val (showTimePicker, setShowTimePicker) =  remember { mutableStateOf(false) }
  val reminderTimingsDialogViewModel = viewModel<ReminderTimingsDialogViewModel>()
  val selectedReminderTiming:MutableState<ReminderTimings?> =  remember { mutableStateOf(null) }
  val reminderTimings = reminderTimingsDialogViewModel.reminderTimings.collectAsState()
  val title = "Reminder Timings"
  
  if(showTimePicker){
    Log.d("$showTimePicker", "ReminderTimingsDialogContent: Show Time Picker")
    TimePicker(
      reminderTiming = selectedReminderTiming.component1(),
      setShowTimePicker = setShowTimePicker,
      reminderReminderTimingsDialogViewModel = reminderTimingsDialogViewModel
    )
  }

  Column {
    Title(title)

    Divider(thickness = 4.dp)

    ReminderTimingsList(
      reminderTimings = reminderTimings.value,
      reminderTimingsDialogViewModel = reminderTimingsDialogViewModel,
      modifier = Modifier.weight(1f,false),
      selectedReminderTiming = selectedReminderTiming,
      setShowTimePicker = setShowTimePicker
    )

    BottomBar(
      selectedReminderTiming = selectedReminderTiming,
      setShowTimePicker = setShowTimePicker
    )
  }
}


@Composable
fun Title(title:String){
  Text(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 0.dp, vertical = 8.dp)
      .zIndex(2f),
    text = title,
    textAlign = TextAlign.Center
  )
}

@Composable
fun ReminderTimingsList(
  reminderTimings: List<ReminderTimings>,
  reminderTimingsDialogViewModel:ReminderTimingsDialogViewModel,
  modifier:Modifier,
  selectedReminderTiming:MutableState<ReminderTimings?>,
  setShowTimePicker:(Boolean) -> Unit
){
  LazyColumn(
    modifier = modifier
  ){
    items(reminderTimings){
      Row {
        Text(modifier = Modifier
          .weight(1f)
          .clickable {
            selectedReminderTiming.value = it
            setShowTimePicker(true)
          },
          text = it.time,
          style = Typography.body1
        )
        Switch(
          checked = it.active,
          onCheckedChange = { currValue ->
            //Create a new instance of reminder timing for flow to recognize update in data
            val updatedReminderTiming = it.copy(active = currValue)
            reminderTimingsDialogViewModel.updateReminderTiming(updatedReminderTiming)
            //TODO("Delete Repeating notification for this time")
          }
        )
        IconButton(
          onClick = {
            reminderTimingsDialogViewModel.deleteReminderTiming(it)
            //TODO("Delete Repeating notification for this time")
          }
        ) {
          Icon(
            painter = painterResource(id = R.drawable.delete_icon),
            contentDescription = "Delete Reminder"
          )
        }
      }
    }
  }
}

@Composable
fun BottomBar(
  selectedReminderTiming:MutableState<ReminderTimings?>,
  setShowTimePicker:(Boolean) -> Unit
){
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .zIndex(2f),
    horizontalArrangement = Arrangement.Center,
  ) {
    IconButton(
      onClick = {
        selectedReminderTiming.value = null
        setShowTimePicker(true)
      }
    ) {
      Icon(
        painter = painterResource(id = R.drawable.add_icon),
        contentDescription = "Add new Reminder"
      )
    }
  }
}