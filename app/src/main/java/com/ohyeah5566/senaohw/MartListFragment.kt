package com.ohyeah5566.senaohw

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ohyeah5566.senaohw.databinding.FragmentMartListBinding
import com.ohyeah5566.senaohw.databinding.ItemMartBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

class MartListFragment : Fragment() {

    private val binding by lazy {
        FragmentMartListBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        MartAdapter {
            viewModel.setMartDetail(it)
            findNavController().navigate(
                R.id.goto_mart_detail_fragment
            )
        }
    }

    private val viewModel by activityViewModels<MartViewModel> {
        MartViewModelProvider(
            MartRepositoryImp(
                getServiceInstance(),
                getDb(requireActivity().applicationContext)
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )
        binding.recyclerView.adapter = adapter
        binding.searchBar.searchEditText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.key = textView.text.toString()
                adapter.refresh()
                (requireActivity() as MainActivity).hideKeyboard()
            }
            true
        }
        binding.refreshLayout.setOnRefreshListener {
            viewModel.clearAll()
            adapter.refresh()
        }

        lifecycleScope.launchWhenResumed {
            viewModel.flow.collect {
                adapter.submitData(it)
            }
        }
        adapterErrorHandle()
        adapterLoadingHandle()
    }
    private fun adapterErrorHandle(){
        lifecycleScope.launchWhenResumed {
            adapter.loadStateFlow.collectError {
                Toast.makeText(requireContext(), it.customMessage(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun adapterLoadingHandle(){
        lifecycleScope.launchWhenResumed {
            adapter.loadStateFlow.distinctUntilChangedBy{ it.refresh }
                .collect {
                    binding.refreshLayout.isRefreshing = it.refresh is LoadState.Loading
                }
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