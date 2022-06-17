package com.ohyeah5566.senaohw

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.NumberFormat

@Parcelize
data class Mart(
    val martId: String = "",
    val martName: String = "",
    val martShortName: String = "",
    val price: Int = 0,
    val finalPrice: Int = 0,
    val imageUrl: String = "",
    val stockAvailable: Int = 0
) : Parcelable {
    fun getFinalPriceText(): String {
        return "$ ${NumberFormat.getNumberInstance().format(finalPrice)}"
    }

}