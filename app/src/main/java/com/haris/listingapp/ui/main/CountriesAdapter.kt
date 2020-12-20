package com.haris.listingapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.haris.listingapp.R
import com.haris.listingapp.data.model.Country
import com.haris.listingapp.databinding.ListItemCountriesBinding
import com.haris.listingapp.utils.Toast

class CountriesAdapter :
    ListAdapter<Country, CountriesAdapter.ViewHolder>(CountriesComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_countries,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { item ->
            with(holder) {
                itemView.tag = item
                bind(item)
            }
        }
    }

    class ViewHolder(private val binding: ListItemCountriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Country) {
            with(binding) {
                country = item
                setClickListener {
                    it.findNavController()
                        .navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(item),
                            FragmentNavigatorExtras(Pair(binding.imgLogo, "flag")))
                }
            }
        }
    }

    object CountriesComparator : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Country, newItem: Country) =
            oldItem == newItem
    }
}