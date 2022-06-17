package com.ohyeah5566.senaohw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ohyeah5566.senaohw.databinding.ActivityMainBinding
import com.ohyeah5566.senaohw.databinding.ItemMartBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        MartAdapter {
            val intent = Intent(this, MartDetailActivity::class.java)
            intent.putExtra(MartDetailActivity.INTENT_KEY_MART, it)
            startActivity(intent)
        }
    }

    private val viewModel by viewModels<MartViewModel> {
        MartViewModelProvider(MartRepositoryRemote(getServiceInstance()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        binding.recyclerView.adapter = adapter

        viewModel.liveData.observe(this) {
            adapter.submitNewList(it)
        }
        viewModel.loadOne()
    }
}

class MartAdapter(
    private inline val itemClickEvent: (mart: Mart) -> Unit
) : RecyclerView.Adapter<MartAdapter.ViewHolder>() {

    private var list: List<Mart> = emptyList()
    fun submitNewList(list: List<Mart>) {
        this.list = list
        notifyDataSetChanged()
    }

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