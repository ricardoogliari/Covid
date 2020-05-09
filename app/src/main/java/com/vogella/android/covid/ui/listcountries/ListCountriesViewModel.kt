package com.vogella.android.covid.ui.listcountries

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vogella.android.covid.core.Core
import com.vogella.android.covid.model.Country
import retrofit2.Callback
import retrofit2.Response

class ListCountriesViewModel : ViewModel() {

    val countries: MutableLiveData<List<Country?>?>  = MutableLiveData()

    fun allCountries(): MutableLiveData<List<Country?>?> {
        return countries
    }

    fun selectedCountry(country: Country){
        Log.e("country", "${country.country}")
    }


    fun loadCountries() {
        //coroutines - multithread
        val countriesCall = Core.service?.listCountries()
        countriesCall?.enqueue(object : Callback<List<Country?>?> {
            override fun onFailure(call: retrofit2.Call<List<Country?>?>, t: Throwable) {}

            override fun onResponse(
                call: retrofit2.Call<List<Country?>?>,
                response: Response<List<Country?>?>
            ) {
                countries.value = response.body()?.sortedBy { it?.country }
            }
        })
    }

}
