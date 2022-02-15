package com.sameep.watertracker.data.static

import com.sameep.watertracker.data.models.Tip

object WaterTips {
  private val tips: List<Tip> = listOf(
    Tip("Try to divide your water intake into 8-12 servings throughout the day."),
    Tip("Drink lots of water soon as you wake up in the morning. This will kickstart your metabolism."),
    Tip("Avoid drinking water too close to meals. Try to keep at-least 30 minutes gap before and after meals."),
    Tip("Always keep a filled water bottle nearby. This will boost your water intake significantly."),
    Tip("Avoid gulping down large volumes of water in a single breath, rather take smaller sips."),
    Tip("Avoid cold water as much as possible, instead drink normal or even better lukewarm water."),
    Tip("Drinking too much water is just as bad as drinking less water. Only drink the amount suggested."),
    Tip("Try to drink water while sitting instead of standing."),
    Tip("Fruits are also high in water content. Don't forget to add them in your water intake!"),
  )

  fun getRandomTip(): Tip {
    val randNum = (tips.indices).random()
    return tips[randNum]
  }
}