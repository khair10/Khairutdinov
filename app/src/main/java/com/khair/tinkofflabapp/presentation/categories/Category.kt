package com.khair.tinkofflabapp.presentation.categories

import java.lang.IllegalArgumentException

sealed class Category(val name: String) {
    object Latest: Category("latest")
    object Hot: Category("hot")
    object Top: Category("top")

    companion object {
        fun getCategory(name: String?): Category {
            return when (name) {
                Latest.name -> Latest
                Hot.name -> Hot
                Top.name -> Top
                else -> throw IllegalArgumentException()
            }
        }
    }
}