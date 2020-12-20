package com.haris.listingapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.haris.listingapp.R
import com.haris.listingapp.databinding.MainFragmentBinding
import java.util.*

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    private val viewModel: MainViewModel by activityViewModels()

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
    }

    private fun subscribeUi() {
        viewModel.allCountries.observe(viewLifecycleOwner, { countries ->
            // Clear Query Before new Data
            binding.searchCountries.setQuery("", true)
            // Submit New Data to View
            adapter.submitList(countries)
            // Handle Empty Data UI
            binding.textEmpty.isGone = countries.isNotEmpty()
            binding.groupCountries.isGone = countries.isEmpty()
            binding.progressCircular.isGone = true
            // Query Listener and Filter
            binding.searchCountries.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = true

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.submitList(countries.orEmpty().filter {
                        it.name.toLowerCase(Locale.getDefault())
                            .contains(newText.orEmpty().trim().toLowerCase(
                                Locale.getDefault()))
                    })
                    return true
                }
            })
        })
        viewModel.progressState.observe(viewLifecycleOwner, {
            binding.progressCircular.isGone = it.not()
        })
    }
}