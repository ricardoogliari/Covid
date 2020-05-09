package com.vogella.android.covid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vogella.android.covid.ui.listcountries.ListCountriesFragment

class ListCountriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_countries_activity)
    }
}
