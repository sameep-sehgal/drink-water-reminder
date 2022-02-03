package com.example.myapplication.utils

import com.example.myapplication.R
import com.example.myapplication.data.models.Beverage

object Beverages {
  private const val WATER = "Water"
  private const val COFFEE = "Coffee"
  private const val TEA = "Tea"
  private const val BEER = "Beer"
  private const val SODA = "Soda"
  private const val JUICE = "Juice"
  private const val MILK = "Milk"
  private const val SMOOTHIE = "Smoothie"
  private const val ENERGY_DRINK = "Energy Drink"
  private const val SOUP = "Soup"
  private const val LEMONADE = "Lemonade"
  private const val WINE = "Wine"

  const val DEFAULT = WATER
  const val DEFAULT_ICON = 0
  const val MAX_ALLOWED_COUNT = 15

  private val water = Beverage(name = WATER,icon = 0, importance = 0)
  private val coffee = Beverage(name = COFFEE,icon = 1, importance = 1)
  private val tea = Beverage(name = TEA,icon = 2, importance = 2)
  private val milk = Beverage(name = MILK,icon = 3, importance = 3)
  private val juice = Beverage(name = JUICE,icon = 4, importance = 4)
  private val lemonade = Beverage(name = LEMONADE,icon = 5, importance = 5)
  private val soda = Beverage(name = SODA,icon = 6, importance = 6)
  private val beer = Beverage(name = BEER,icon = 7, importance = 7)
  private val smoothie = Beverage(name = SMOOTHIE,icon = 8, importance = 8)
  private val energyDrink = Beverage(name = ENERGY_DRINK,icon = 9, importance = 9)
  private val soup = Beverage(name = SOUP,icon = 10, importance = 10)
  private val wine = Beverage(name = WINE,icon = 11, importance = 11)

  val defaultBeverages = listOf(
    water,
    coffee,
    tea,
    milk,
    juice,
    lemonade,
    soda,
    beer,
    smoothie,
    energyDrink,
    soup,
    wine
  )

  val IMAGE_MAPPER = listOf(
    R.drawable.water_icon,
    R.drawable.coffee_icon,
    R.drawable.tea_icon,
    R.drawable.milk_icon,
    R.drawable.juice_icon,
    R.drawable.lemonade_icon,
    R.drawable.soda_icon,
    R.drawable.beer_icon,
    R.drawable.smoothie_icon,
    R.drawable.energy_drink_icon,
    R.drawable.soup_icon,
    R.drawable.wine_icon,
  )
}