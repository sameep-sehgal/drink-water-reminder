package com.sameep.watertracker

import android.app.NotificationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import dagger.hilt.android.AndroidEntryPoint
import com.sameep.watertracker.ui.CollectUserData
import com.sameep.watertracker.ui.MainAppContent
import com.sameep.watertracker.utils.ReminderReceiverUtil

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val preferenceDataStoreViewModel: PreferenceDataStoreViewModel by viewModels()
  private val roomDatabaseViewModel: RoomDatabaseViewModel by viewModels()
  private var notificationManager: NotificationManager? = null

  private var mInterstitialAd: InterstitialAd? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ReminderReceiverUtil.createNotificationChannel(this)

    var adRequest = AdRequest.Builder().build()

    InterstitialAd.load(this,"ca-app-pub-8928129645511623/5409049206", adRequest, object : InterstitialAdLoadCallback() {
      override fun onAdFailedToLoad(adError: LoadAdError) {
        Log.d(javaClass.name, adError?.toString())
        mInterstitialAd = null
      }

      override fun onAdLoaded(interstitialAd: InterstitialAd) {
        Log.d(javaClass.name, "Ad was loaded.")
        mInterstitialAd = interstitialAd
      }
    })

    val loadInterstitialAd = {
        if (mInterstitialAd != null) {
          mInterstitialAd?.show(this)
        } else {
          Log.d(javaClass.name, "The interstitial ad wasn't ready yet.")
        }
      }

    preferenceDataStoreViewModel.isUserInfoCollected.observe(this){
      if(it == true) {
        setContent {
          MainAppContent(
            roomDatabaseViewModel = roomDatabaseViewModel,
            preferenceDataStoreViewModel = preferenceDataStoreViewModel,
            loadInterstitialAd = loadInterstitialAd
          )
        }
      }else{
        setContent {
          CollectUserData(
            roomDatabaseViewModel = roomDatabaseViewModel,
            preferenceDataStoreViewModel = preferenceDataStoreViewModel
          )
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()
    notificationManager?.cancelAll()
    roomDatabaseViewModel.refreshData()
    if (mInterstitialAd != null) {
      mInterstitialAd?.show(this)
    } else {
      Log.d(javaClass.name, "The interstitial ad wasn't ready yet.")
    }
  }
}
