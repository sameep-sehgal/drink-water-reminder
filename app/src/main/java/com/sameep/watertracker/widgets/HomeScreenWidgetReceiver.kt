package com.sameep.watertracker.widgets

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.sameep.watertracker.MainActivity
import com.sameep.watertracker.R
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.data.preferencedatastore.PreferenceDataStore
import com.sameep.watertracker.data.preferencedatastore.dataStore
import com.sameep.watertracker.data.roomdatabase.WaterDatabase
import com.sameep.watertracker.data.roomdatabase.WaterDatabaseDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeScreenWidgetReceiver : AppWidgetProvider() {

    private val ADD_WATER_BUTTON_CLICK = "addWaterButtonClick"
    var db: WaterDatabaseDao? = null
    var preferenceDataStore: DataStore<Preferences>? = null
    var appWidgetManager: AppWidgetManager? = null
    var appWidgetIds: IntArray? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if(context != null) {
            db = WaterDatabase.getInstance(context).waterDatabaseDao()
            preferenceDataStore = context.dataStore
        }
        if (ACTION_APPWIDGET_UPDATE == intent?.action) {
            Log.d(javaClass.name, "Inside " + ADD_WATER_BUTTON_CLICK + " " + appWidgetIds + " " + appWidgetManager)
            appWidgetIds?.forEach { appWidgetId ->
                val pendingIntent: PendingIntent = PendingIntent.getActivity(
                    context,
                    10,
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                var glassCapacity: Int?
                var mugCapacity: Int?
                var bottleCapacity: Int?
                var todaysWaterRecord: DailyWaterRecord?

                GlobalScope.launch {
                    preferenceDataStore?.data?.first {
                        glassCapacity = it[PreferenceDataStore.PreferencesKeys.GLASS_CAPACITY]
                        mugCapacity = it[PreferenceDataStore.PreferencesKeys.MUG_CAPACITY]
                        bottleCapacity = it[PreferenceDataStore.PreferencesKeys.BOTTLE_CAPACITY]
                        todaysWaterRecord = db?.getDailyWaterRecord()?.first()
                        val views: RemoteViews = RemoteViews(
                            context?.packageName,
                            R.layout.home_widget_layout
                        ).apply {
                            todaysWaterRecord?.let {
                                setProgressBar(
                                    R.id.water_progress_bar,
                                    todaysWaterRecord!!.goal,
                                    todaysWaterRecord!!.currWaterAmount,
                                    false
                                )
                            }
                            Log.d(
                                javaClass.name,
                                "Inside OnReceive update on button click " + todaysWaterRecord + " " + todaysWaterRecord?.goal + " " + glassCapacity
                            )
                        }

                        appWidgetManager?.updateAppWidget(appWidgetId, views)
                        true
                    }
                }
            }
        }
        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        Log.d(javaClass.name, "Inside onUpdate" + ADD_WATER_BUTTON_CLICK + " " + appWidgetIds + " " + appWidgetManager)
        this.appWidgetManager = appWidgetManager
        this.appWidgetIds = appWidgetIds
        appWidgetIds?.forEach { appWidgetId ->
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                context,
                10,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            var glassCapacity: Int? = null
            var mugCapacity: Int? = null
            var bottleCapacity: Int? = null
            var waterGoal: Int? = null
            var todaysWaterRecord: DailyWaterRecord? = null
            GlobalScope.launch {
                preferenceDataStore?.data?.first {
                    glassCapacity = it[PreferenceDataStore.PreferencesKeys.GLASS_CAPACITY]
                    mugCapacity = it[PreferenceDataStore.PreferencesKeys.MUG_CAPACITY]
                    bottleCapacity = it[PreferenceDataStore.PreferencesKeys.BOTTLE_CAPACITY]
                    waterGoal = it[PreferenceDataStore.PreferencesKeys.DAILY_WATER_GOAL]
                    todaysWaterRecord = db?.getDailyWaterRecord()?.first()
                    val views: RemoteViews = RemoteViews(
                        context?.packageName,
                        R.layout.home_widget_layout
                    ).apply {
                        setOnClickPendingIntent(R.id.home_widget_open_app_button, pendingIntent)
                        setOnClickPendingIntent(R.id.glass_water_button, getPendingSelfIntent(context, glassCapacity))
                        setOnClickPendingIntent(R.id.mug_water_button, getPendingSelfIntent(context, mugCapacity))
                        setOnClickPendingIntent(R.id.bottle_water_button, getPendingSelfIntent(context, bottleCapacity))
                        todaysWaterRecord?.let { setProgressBar(R.id.water_progress_bar, todaysWaterRecord!!.goal, todaysWaterRecord!!.currWaterAmount, false) }
                        Log.d(javaClass.name,"Inside Remote View Apply " + todaysWaterRecord + " " + todaysWaterRecord?.goal + " " + glassCapacity)
                    }

                    appWidgetManager?.updateAppWidget(appWidgetId, views)
                    true
                }
            }

            Log.d(
                "TAG",
                "onReceive:outsise globalsocpoe"
            )
        }
    }

    private fun getPendingSelfIntent(context: Context?, waterAmount:Int?): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = ACTION_APPWIDGET_UPDATE
        return PendingIntent.getBroadcast(context, 322, intent, FLAG_IMMUTABLE)
    }
}