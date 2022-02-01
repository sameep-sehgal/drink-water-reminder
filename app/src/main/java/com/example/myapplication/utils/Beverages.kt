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

  private val water = Beverage(name = WATER,icon = 0)
  private val coffee = Beverage(name = COFFEE,icon = 1)
  private val tea = Beverage(name = TEA,icon = 2)
  private val milk = Beverage(name = MILK,icon = 3)
  private val juice = Beverage(name = JUICE,icon = 4)
  private val lemonade = Beverage(name = LEMONADE,icon = 5)
  private val soda = Beverage(name = SODA,icon = 6)
  private val beer = Beverage(name = BEER,icon = 7)
  private val smoothie = Beverage(name = SMOOTHIE,icon = 8)
  private val energyDrink = Beverage(name = ENERGY_DRINK,icon = 9)
  private val soup = Beverage(name = SOUP,icon = 10)
  private val wine = Beverage(name = WINE,icon = 11)

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

  val IMAGE_MAPPER = hashMapOf(
    water.icon to R.drawable.water_icon,
    coffee.icon to R.drawable.coffee_icon,
    tea.icon to R.drawable.tea_icon,
    milk.icon to R.drawable.milk_icon,
    juice.icon to R.drawable.juice_icon,
    lemonade.icon to R.drawable.lemonade_icon,
    soda.icon to R.drawable.soda_icon,
    beer.icon to R.drawable.beer_icon,
    smoothie.icon to R.drawable.smoothie_icon,
    energyDrink.icon to R.drawable.energy_drink_icon,
    soup.icon to R.drawable.soup_icon,
    wine.icon to R.drawable.wine_icon,
  )
}