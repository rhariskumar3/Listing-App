package com.haris.listingapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.haris.listingapp.R
import com.haris.listingapp.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {

    private lateinit var binding: DetailFragmentBinding

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        config()
        return binding.root
    }

    private fun config() {
        binding.viewModel = viewModel
        binding.country = args.country
    }
}