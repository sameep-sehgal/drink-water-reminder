package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import com.example.myapplication.ui.screens.collectuserdata.CollectUserData
import com.example.myapplication.ui.theme.ApplicationTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectUserDataActivity : ComponentActivity(){
  
  private val TAG = CollectUserDataActivity::class.java.simpleName

  @ExperimentalPagerApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ApplicationTheme {
        Surface {
          val preferenceDataStoreViewModel: PreferenceDataStoreViewModel by viewModels()
          Log.d(TAG, "onCreate: $TAG")
          CollectUserData(preferenceDataStoreViewModel)
        }
      }
    }
  }
}