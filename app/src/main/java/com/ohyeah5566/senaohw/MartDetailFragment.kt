package com.ohyeah5566.senaohw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.load
import com.ohyeah5566.senaohw.databinding.FragmentMartDetailBinding

class MartDetailFragment : Fragment() {
    private val binding by lazy {
        FragmentMartDetailBinding.inflate(layoutInflater)
    }

    private val viewModel by activityViewModels<MartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()

        viewModel.martDetail.observe(this) { mart ->
            with(binding) {
                imgView.load(mart.imageUrl)
                martNameTv.text = mart.martName
                martPriceTv.text = mart.getFinalPriceText()
                martIdTv.text = getString(R.string.item_mart_id, mart.martId)
            }
        }
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

        }
    }
}