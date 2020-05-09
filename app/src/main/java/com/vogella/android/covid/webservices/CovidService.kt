package com.vogella.android.covid.webservices

import com.vogella.android.covid.model.Country
import com.vogella.android.covid.model.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidService {

    @GET("countries")
    fun listCountries(): Call<List<Country?>?>?

    @GET("country/{country}/status/recovered?from=2020-04-01&to=2020-05-09")
    fun listData(@Path("country") country: String): Call<List<Data?>?>?


}