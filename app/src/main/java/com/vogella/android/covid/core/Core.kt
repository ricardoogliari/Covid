package com.vogella.android.covid.core

import android.app.Application
import com.vogella.android.covid.webservices.CovidService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Core : Application() {

    //objeto estático e público
    companion object Factory {
        var service: CovidService? = null
    }

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.covid19api.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(CovidService::class.java)
    }

}