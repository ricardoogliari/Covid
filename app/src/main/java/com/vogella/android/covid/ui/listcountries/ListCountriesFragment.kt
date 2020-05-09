package com.vogella.android.covid.ui.listcountries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vogella.android.covid.R
import com.vogella.android.covid.adapters.CountriesAdapter
import com.vogella.android.covid.databinding.ListCountriesFragmentBinding
import com.vogella.android.covid.model.Country

class ListCountriesFragment : Fragment() {

    companion object {
        fun newInstance() = ListCountriesFragment()
    }

    private lateinit var viewModel: ListCountriesViewModel

    var mBinding: ListCountriesFragmentBinding? = null
    var mAdapter: CountriesAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //TODO: comentar sobre este ponto chave
        viewModel = ViewModelProvider(this).get(ListCountriesViewModel::class.java)

        //TODO: comentar sobre este ponto chave
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.list_countries_fragment, container, false
        )

        //TODO: comentar sobre este ponto chave
        mBinding?.lifecycleOwner = this;

        mBinding?.viewModel = viewModel;
        mBinding?.listCountries?.layoutManager = LinearLayoutManager(activity)

        viewModel.allCountries().observe(viewLifecycleOwner, Observer<List<Country?>?>{ users ->
            // update UI
            users?.let {
                mAdapter = CountriesAdapter(it, this@ListCountriesFragment)
                mBinding?.listCountries?.adapter = mAdapter
            }
        })
        viewModel.loadCountries()

        //TODO: comentar sobre este ponto chave
        return mBinding?.getRoot()!!
    }

}
