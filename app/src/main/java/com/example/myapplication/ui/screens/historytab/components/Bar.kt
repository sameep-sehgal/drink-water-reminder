package com.example.myapplication.ui.screens.historytab.components

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.SelectedItemColor
import com.example.myapplication.utils.DateString

@Composable
fun Bar(
  date:String,
  waterAmount:Int,
  waterGoal:Int,
  setSelectedDate: (String) -> Unit,
  selectedDate: String
) {
  val currentPercentage = remember { Animatable(0f) }
  val selected = selectedDate == date

  LaunchedEffect(waterGoal,waterAmount) {
    if(waterAmount/waterGoal.toFloat() < 1f){
      currentPercentage.animateTo(
        waterAmount/waterGoal.toFloat(),
        animationSpec = tween(durationMillis = 1000)
      )
    }else{
      currentPercentage.animateTo(
        1f,
        animationSpec = tween(durationMillis = 1000)
      )
    }
  }
  var cc by remember { mutableStateOf(IntSize.Zero) }

  Column(
    modifier = Modifier
      .selectable(
        enabled = true,
        selected = selected,
        onClick = { setSelectedDate(date) }
      ).background(
        color = if(selected) SelectedItemColor() else Color.Transparent
      )
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
      Text(
        text = "${(currentPercentage.value*100).toInt()}%",
        fontSize = 14.sp,
        color = setSelectedTextColor(selected = selected)
      )
      Divider(
        modifier = Modifier
          .width(40.dp)
          .padding(0.dp, 2.dp, 0.dp, 1.dp),
        color = setSelectedTextColor(selected = selected),
        thickness = 1.dp
      )
      Column(
        modifier = Modifier
          .height(220.dp)
          .width(50.dp)
          .onGloballyPositioned {
            cc = it.size
          }
          .background(
            Brush.verticalGradient(
              colors = listOf(
                if (selected) MaterialTheme.colors.primary else Color.Cyan,
                Color.Transparent
              ),
              startY = (cc.height + 6f) * (1 - currentPercentage.value + 0.01f),
              endY = cc.height * (1 - currentPercentage.value + 0.01f)
            )
          )
      ){
        Row() {
          Log.d("TAG", "DataGraphBar: ${currentPercentage.value}")

        }
      }
      Divider()
      Text(
        text = DateString.clipToMMDD(date),
        fontSize = 14.sp,
        color = setSelectedTextColor(selected = selected)
      )
    }
  }
}

@Composable
fun setSelectedTextColor(selected:Boolean): Color {
  if(selected)
    return MaterialTheme.colors.primary
  else
    return MaterialTheme.colors.onSurface
}