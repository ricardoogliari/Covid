package com.vogella.android.covid.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Country (

    @SerializedName("Country")
    val country: String,

    @SerializedName("Slug")
    val slug: String,

    @SerializedName("ISO2")
    val iso2: String

) : Serializable