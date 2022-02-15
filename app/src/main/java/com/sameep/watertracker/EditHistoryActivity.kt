package com.sameep.watertracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.sameep.watertracker.ui.screens.edithistory.EditHistory
import com.sameep.watertracker.ui.theme.ApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditHistoryActivity : ComponentActivity() {
  private val TAG = MainActivity::class.java.simpleName
  private val preferenceDataStoreViewModel: PreferenceDataStoreViewModel by viewModels()
  private val roomDatabaseViewModel: RoomDatabaseViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent{
      ApplicationTheme{
        Scaffold (
          topBar = {
            TopAppBar(
              title = { Text(text = "Edit History") },
              navigationIcon = {
                IconButton(onClick = { finish() }) {
                  Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                  )
                }
              },
              backgroundColor = MaterialTheme.colors.primary
            )

          },
        ){
          EditHistory(
            roomDatabaseViewModel = roomDatabaseViewModel,
            preferenceDataStoreViewModel = preferenceDataStoreViewModel
          )
        }
      }
    }
  }
}