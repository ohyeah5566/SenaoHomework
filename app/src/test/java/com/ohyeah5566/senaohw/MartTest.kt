package com.ohyeah5566.senaohw

import org.junit.Assert
import org.junit.Test


internal class MartTest {

    @Test
    fun testMonkeyStr0() {
        val mart = Mart(finalPrice = 0)
        Assert.assertEquals("$ 0", mart.getFinalPriceText())
    }

    @Test
    fun testMonkeyStr1000() {
        val mart = Mart(finalPrice = 1000)
        Assert.assertEquals("$ 1,000", mart.getFinalPriceText())
    }

    @Test
    fun testMonkeyStr10000() {
        val mart = Mart(finalPrice = 10000)
        Assert.assertEquals("$ 10,000", mart.getFinalPriceText())
    }

    @Test
    fun testMonkeyStr100000() {
        val mart = Mart(finalPrice = 100000)
        Assert.assertEquals("$ 100,000", mart.getFinalPriceText())
    }
}