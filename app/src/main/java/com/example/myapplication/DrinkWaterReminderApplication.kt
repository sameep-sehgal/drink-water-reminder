package com.example.myapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DrinkWaterReminderApplication: Application()

//Creates a dependency container at top application level
//Now Hilt can inject dependencies in any part of the app