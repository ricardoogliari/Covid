package com.vogella.android.covid.ui.listcountries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vogella.android.covid.core.Core
import com.vogella.android.covid.model.Data
import retrofit2.Callback
import retrofit2.Response

class DetailCountryViewModel : ViewModel() {

    private val dataset: MutableLiveData<List<Data?>?> by lazy {
        MutableLiveData<List<Data?>?>()
    }

    fun getData(): LiveData<List<Data?>?> {
        return dataset
    }


    public fun loadDataset(country: String) {
        val call = Core.service?.listData(country)
        call?.enqueue(object : Callback<List<Data?>?> {
            override fun onFailure(call: retrofit2.Call<List<Data?>?>, t: Throwable) {}

            override fun onResponse(
                call: retrofit2.Call<List<Data?>?>,
                response: Response<List<Data?>?>
            ) {
                dataset.value = response.body()?.sortedBy { it?.date }
            }
        })
    }

}