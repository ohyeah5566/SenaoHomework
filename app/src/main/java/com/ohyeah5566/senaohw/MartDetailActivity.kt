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
        val mart = Mart(
            martName = "iPhone 12 Pro Max 256GB【下殺97折 送保護貼兌換券】",
            finalPrice = 39950,
            imageUrl = "https://pdinfo.senao.com.tw/octopus/contents/99b404a6bcfb4a74a27e4a10746fb258.jpg",
            martId = "12345"
        )
        with(binding){
            imgView.load(mart.imageUrl)
            martNameTv.text = mart.martName
            martPriceTv.text = mart.getFinalPriceText()
            martIdTv.text = getString(R.string.item_mart_id, mart.martId)
        }
    }

}