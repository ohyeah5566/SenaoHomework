package com.ohyeah5566.senaohw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.ohyeah5566.senaohw.databinding.ActivityMartDetailBinding

class MartDetailActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMartDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val mart = intent.getParcelableExtra<Mart>(INTENT_KEY_MART)!!
        with(binding) {
            imgView.load(mart.imageUrl)
            martNameTv.text = mart.martName
            martPriceTv.text = mart.getFinalPriceText()
            martIdTv.text = getString(R.string.item_mart_id, mart.martId)
        }
    }

    companion object {
        const val INTENT_KEY_MART = "mart"
    }

}