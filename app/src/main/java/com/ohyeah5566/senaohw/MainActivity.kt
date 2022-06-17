package com.ohyeah5566.senaohw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ohyeah5566.senaohw.databinding.ActivityMainBinding
import com.ohyeah5566.senaohw.databinding.ItemMartBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        binding.recyclerView.adapter = MartAdapter(listOf(
            Mart(martName = "iPhone 12 Pro Max 256GB【下殺97折 送保護貼兌換券】", finalPrice = 39950, imageUrl = "https://pdinfo.senao.com.tw/octopus/contents/99b404a6bcfb4a74a27e4a10746fb258.jpg"),
            Mart(martName = "iPhone 12 Pro Max 256GB【下殺97折 送保護貼兌換券】", finalPrice = 39950, imageUrl = "https://pdinfo.senao.com.tw/octopus/contents/99b404a6bcfb4a74a27e4a10746fb258.jpg"),
            Mart(martName = "iPhone 12 Pro Max 256GB【下殺97折 送保護貼兌換券】", finalPrice = 39950, imageUrl = "https://pdinfo.senao.com.tw/octopus/contents/99b404a6bcfb4a74a27e4a10746fb258.jpg"),
            Mart(martName = "iPhone 12 Pro Max 256GB【下殺97折 送保護貼兌換券】", finalPrice = 39950, imageUrl = "https://pdinfo.senao.com.tw/octopus/contents/99b404a6bcfb4a74a27e4a10746fb258.jpg"),
            Mart(martName = "iPhone 12 Pro Max 256GB【下殺97折 送保護貼兌換券】", finalPrice = 39950, imageUrl = "https://pdinfo.senao.com.tw/octopus/contents/99b404a6bcfb4a74a27e4a10746fb258.jpg")
        )){
            val intent = Intent(this, MartDetailActivity::class.java)
            intent.putExtra(MartDetailActivity.INTENT_KEY_MART, it)
            startActivity(intent)
        }
    }
}

class MartAdapter(
    private val list: List<Mart>,
    private inline val itemClickEvent: (mart: Mart) -> Unit
) : RecyclerView.Adapter<MartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position],)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(
        val binding: ItemMartBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(mart: Mart) {
            binding.imv.load(mart.imageUrl)
            binding.martNameTv.text = mart.martName
            binding.finalPriceTv.text = mart.getFinalPriceText()
            binding.root.setOnClickListener {
                itemClickEvent.invoke(mart)
            }
        }
    }


}