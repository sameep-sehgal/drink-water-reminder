package com.example.myapplication.ui.screens.edithistory.components

import android.widget.CalendarView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.utils.DateString

@Composable
fun RenderCalendar(
  setSelectedDate:(String) -> Unit,
  firstWaterDataDate: String,
  todaysDate:String
) {
  val onCalendarDateChangeListener = CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
    setSelectedDate(DateString.convertToDateString(year, month, dayOfMonth))
  }

  AndroidView(
    factory = {
      val calendarView = CalendarView(it)
      calendarView.setOnDateChangeListener(onCalendarDateChangeListener)
      calendarView.scaleX = 1.1f
      calendarView.scaleY = 0.9f
      calendarView
    },
    update = {
      if(firstWaterDataDate != DateString.NOT_SET) {
        it.minDate = DateString.getCalendarInstance(firstWaterDataDate).timeInMillis
      }
      it.maxDate = DateString.getCalendarInstance(todaysDate).timeInMillis
    }
  )
}