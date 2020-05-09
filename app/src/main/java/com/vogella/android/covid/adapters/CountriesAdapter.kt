package com.vogella.android.covid.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vogella.android.covid.R
import com.vogella.android.covid.databinding.ItemBinding
import com.vogella.android.covid.model.Country
import com.vogella.android.covid.ui.listcountries.ListCountriesViewModel

class CountriesAdapter(
    var countries: List<Country?>,
    var storeOwner: ViewModelStoreOwner) : RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

    //TODO: comentar sobre este ponto chave - viewholder e binding
    class CountryViewHolder(var itemBinding: ItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CountryViewHolder {
        val employeeListItemBinding: ItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.item, viewGroup, false
        )
        return CountryViewHolder(employeeListItemBinding)
    }

    override fun onBindViewHolder(viewHolder: CountryViewHolder, position: Int) {
        countries[position]?.let {
            viewHolder.itemBinding.country = it
            viewHolder.itemBinding.viewModel = ViewModelProvider(storeOwner).get(ListCountriesViewModel::class.java)
            //android:onClick="@{() -> viewModel.selectedCountry(country)}"
            val bundle = Bundle()
            bundle.putSerializable("country", it)
            viewHolder.itemBinding.tvCountryName.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.select_country_action, bundle)
            )
        }
    }

    override fun getItemCount(): Int {
        return if (countries != null) {
            countries.size
        } else {
            0
        }
    }

}