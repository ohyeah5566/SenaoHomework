package com.ohyeah5566.senaohw

import java.text.NumberFormat
import java.util.*

data class Mart(
    val martId: String = "",
    val martName: String = "",
    val martShortName: String = "",
    val price: Int = 0,
    val finalPrice: Int = 0,
    val imageUrl: String = "",
    val stockAvailable: Int = 0
) {
    fun getFinalPriceText(): String {
        return "$ ${NumberFormat.getNumberInstance().format(finalPrice)}"
    }

}