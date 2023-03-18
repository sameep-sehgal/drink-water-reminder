package com.sameep.watertracker.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.sameep.watertracker.MainActivity
import com.sameep.watertracker.R

class HomeScreenWidgetReceiver : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appWidgetIds?.forEach { appWidgetId ->
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                context,
                0,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val views: RemoteViews = RemoteViews(
                context?.packageName,
                R.layout.home_widget_layout
            ).apply {
                setOnClickPendingIntent(R.id.home_widget_open_app_button, pendingIntent)
            }

            appWidgetManager?.updateAppWidget(appWidgetId, views)
        }
    }
}