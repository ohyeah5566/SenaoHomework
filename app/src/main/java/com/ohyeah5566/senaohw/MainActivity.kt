package com.ohyeah5566.senaohw

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
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
        MartViewModelProvider(
            MartRepositoryImp(
                getServiceInstance(),
                getDb(applicationContext)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        binding.recyclerView.adapter = adapter
        binding.searchBar.searchEditText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.key = textView.text.toString()
                adapter.refresh()
                hideKeyboard()
            }
            true
        }
        binding.refreshLayout.setOnRefreshListener {
            viewModel.clearAll()
            adapter.refresh()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.flow.collect {
                binding.refreshLayout.isRefreshing = false
                adapter.submitData(it)
            }
        }
    }

    private fun hideKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

class MartAdapter(
    private inline val itemClickEvent: (mart: Mart) -> Unit
) : PagingDataAdapter<Mart, MartAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Mart>() {
    override fun areItemsTheSame(oldItem: Mart, newItem: Mart): Boolean {
        return oldItem.martId == newItem.martId
    }

    override fun areContentsTheSame(oldItem: Mart, newItem: Mart): Boolean {
        return oldItem == newItem
    }
}) {

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
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemMartBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(mart: Mart?) {
            mart?.let {
                binding.imv.load(mart.imageUrl)
                binding.martNameTv.text = mart.martName
                binding.finalPriceTv.text = mart.getFinalPriceText()
                binding.root.setOnClickListener {
                    itemClickEvent.invoke(mart)
                }
            }
        }
    }
}