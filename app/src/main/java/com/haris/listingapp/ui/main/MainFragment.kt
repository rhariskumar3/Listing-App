package com.haris.listingapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.haris.listingapp.R
import com.haris.listingapp.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var adapter: CountriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        config()
        return binding.root
    }

    private fun config() {
        lifecycle.addObserver(viewModel)
        binding.viewModel = viewModel

        adapter = CountriesAdapter()
        binding.recyclerCountries.adapter = adapter
        subscribeUi()

        binding.searchCountries.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText.orEmpty().trim())
                return true
            }
        })
    }

    private fun subscribeUi() {
        viewModel.countries.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }
}