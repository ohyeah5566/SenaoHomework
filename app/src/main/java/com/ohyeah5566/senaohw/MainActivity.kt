package com.ohyeah5566.senaohw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ohyeah5566.senaohw.databinding.ActivityMainBinding
import com.ohyeah5566.senaohw.databinding.ItemMartBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.adapter = MartAdapter(emptyList())
    }
}

class MartAdapter(
    private val list: List<Mart>
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
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(
        val binding: ItemMartBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(mart: Mart) {

        }
    }


}