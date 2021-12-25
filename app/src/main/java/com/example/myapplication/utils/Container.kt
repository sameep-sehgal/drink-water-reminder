package com.example.myapplication.utils

import com.example.myapplication.R

class Container {
  companion object {
    const val BOTTLE = 0
    const val MUG = 1
    const val GLASS = 2
    const val CUP = 3

    //TODO("Add more containers here")
    val IMAGE_MAPPER = hashMapOf(
      BOTTLE to R.drawable.bottle_4,
      MUG to R.drawable.mug_3,
      GLASS to R.drawable.glass_2,
      CUP to R.drawable.cup_1
    )
  }
}