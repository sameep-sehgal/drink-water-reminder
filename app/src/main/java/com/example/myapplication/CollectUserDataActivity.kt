package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.example.myapplication.ui.screens.collectuserdata.CollectUserData
import com.example.myapplication.ui.theme.ApplicationTheme

class CollectUserDataActivity : ComponentActivity(){
  
  private val TAG = CollectUserDataActivity::class.java.simpleName
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ApplicationTheme {
        Surface {
          Log.d(TAG, "onCreate: $TAG")
          CollectUserData()
        }
      }
    }
  }
}