package com.vogella.android.covid.model

import com.google.gson.annotations.SerializedName

data class Data (

    @SerializedName("Date")
    val date: String,

    @SerializedName("Cases")
    val cases: Int

)