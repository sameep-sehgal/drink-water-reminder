package com.example.myapplication.utils

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

  private val water = Beverage(WATER, 0)
  private val coffee = Beverage(COFFEE, 1)
  private val tea = Beverage(TEA, 2)
  private val milk = Beverage(MILK, 0)
  private val juice = Beverage(JUICE, 0)
  private val beer = Beverage(BEER, 0)
  private val soda = Beverage(SODA, 0)
  private val smoothie = Beverage(SMOOTHIE, 0)
  private val energyDrink = Beverage(ENERGY_DRINK, 0)
  private val soup = Beverage(SOUP, 0)
  private val lemonade = Beverage(LEMONADE, 0)
  private val wine = Beverage(WINE, 0)

  val defaultBeverages = listOf(
    water,
    coffee,
    tea,
    milk,
    juice,
    beer,
    soda,
    smoothie,
    energyDrink,
    soup,
    lemonade,
    wine
  )
}