package com.example.myapplication.data.static

import com.example.myapplication.data.models.Tip

object WaterTips {
  private val tips: List<Tip> = listOf(
    Tip("Try to divide your water intake into 8-12 servings throughout the day."),
    Tip("Drink lots of water soon as you wake up in the morning. This will kickstart your metabolism."),
    Tip("Avoid drinking water too close to meals. Try to keep at-least 30 minutes gap before and after the meal."),
    Tip("Always keep a filled water bottle nearby. This will boost your water intake significantly."),
    Tip("Avoid gulping down large volumes of water in a single breath, rather take smaller sip."),
    Tip("Avoid cold water as much as possible, instead drink normal or even better lukewarm water. Chilled water shrinks blood vessel and affects digestion."),
    Tip("Drinking too much water is just as bad as drinking less water. Only drink the amount suggested. A little extra is fine."),
    Tip("Try to drink water while sitting instead of standing."),
  )

  fun getRandomTip(): Tip{
    val randNum = (0..tips.size).random()
    return tips[randNum]
  }
}