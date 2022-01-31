package com.example.myapplication.utils

import com.example.myapplication.data.models.VegFruit

object VegFruits {
  //Fruits
  private const val APPLE = "Apple"
  private const val BANANA = "Banana"
  private const val BERRIES = "Berries"
  private const val CHERRY = "Cherry"
  private const val GRAPES = "Grapes"
  private const val MANGO = "Mango"
  private const val MELON = "Melon"
  private const val ORANGE = "Orange"
  private const val PEACH = "Peach"
  private const val PEAR = "Pear"
  private const val PAPAYA = "PAPAYA"
  private const val PINEAPPLE = "Pineapple"
  private const val STRAWBERRY = "Strawberry"
  private const val TOMATO = "Tomato"
  private const val WATERMELON = "Watermelon"

  val defaultFruits = listOf(
    VegFruit(APPLE,84, APPLE,true),
    VegFruit(BANANA,74, BANANA,true),
    VegFruit(BERRIES,87, BERRIES,true),
    VegFruit(CHERRY,81, CHERRY,true),
    VegFruit(GRAPES,81, GRAPES,true),
    VegFruit(MANGO,83, MANGO,true),
    VegFruit(MELON,90, MELON,true),
    VegFruit(ORANGE,87, ORANGE,true),
    VegFruit(PEACH,89, PEACH,true),
    VegFruit(PEAR,84, PEAR,true),
    VegFruit(PINEAPPLE,86, PINEAPPLE,true),
    VegFruit(PAPAYA,88, PAPAYA,true),
    VegFruit(STRAWBERRY,92, STRAWBERRY,true),
    VegFruit(TOMATO,94, TOMATO,false),
    VegFruit(WATERMELON,91, WATERMELON,true),
  )

  //Vegetables
  private const val BEETROOT = "Beetroot"
  private const val BROCCOLI = "Broccoli"
  private const val CABBAGE = "Cabbage"
  private const val CARROT = "Carrot"
  private const val CUCUMBER = "Cucumber"
  private const val LEAFY_VEGGIES = "Leafy Veggies"
  private const val OLIVE = "Olive"
  private const val ONION = "Onion"
  private const val PEAS = "Onion"
  private const val PEPPER = "Pepper"
  private const val RADISH = "Radish"
  private const val ZUCCHINI = "Zucchini"

  val defaultVegetables = listOf(
    VegFruit(BEETROOT,87, BEETROOT,false),
    VegFruit(BROCCOLI,91, BROCCOLI,false),
    VegFruit(CABBAGE,92, CABBAGE,false),
    VegFruit(CARROT,87, CARROT,false),
    VegFruit(CUCUMBER,96, CUCUMBER,false),
    VegFruit(LEAFY_VEGGIES,94, LEAFY_VEGGIES,false),
    VegFruit(OLIVE,80, OLIVE,false),
    VegFruit(ONION,88, ONION,false),
    VegFruit(PEAS,79, PEAS,false),
    VegFruit(PEPPER,92, PEPPER,false),
    VegFruit(RADISH,95, RADISH,false),
    VegFruit(ZUCCHINI,95, ZUCCHINI,false),
  )
}