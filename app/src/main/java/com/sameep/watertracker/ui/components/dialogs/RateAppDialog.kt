package com.sameep.watertracker.ui.components.dialogs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RatingBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.sameep.watertracker.BuildConfig

@Composable
fun RateAppDialog(
  setShowDialog: (Boolean) -> Unit,
  context: Context
) {
  var rating by remember {
    mutableStateOf(0f)
  }

  val ratingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, ratingValue, fromUser ->
    rating = ratingValue
    if (fromUser) {
      val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)
      )
      context.startActivity(intent)
    }
  }

  Dialog(
    onDismissRequest = {
      setShowDialog(false)
    },
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .clip(RoundedCornerShape(15.dp))
        .background(MaterialTheme.colors.surface)
        .padding(8.dp)
    ) {
      Text(
        text = "Rate the App\n",
        fontSize = 20.sp,
        textAlign = TextAlign.Center
      )
      Text(
        text = "If you liked the app, Please consider giving a 5⭐!",
        fontSize = 13.sp,
        textAlign = TextAlign.Center
      )
      AndroidView(
        factory = {
          val ratingBar = RatingBar(context)
          ratingBar.onRatingBarChangeListener = ratingBarChangeListener
          ratingBar.numStars = 5
          ratingBar
        },
        update = {
          it.rating = rating
        }
      )
      Text(
        text = "Also Feel Free to provide suggestions via mail or in App Reviews on Play Store so that we can keep improving the app!\uD83D\uDC96",
        fontSize = 12.sp,
        textAlign = TextAlign.Center
      )
    }
  }
}