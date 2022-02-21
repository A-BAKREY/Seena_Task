package com.example.seenatask.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecyclerModel(

    @SerializedName("title")
    @Expose
    val title: String? = null,
    @SerializedName("published by")
    @Expose
    val published_by: String? = null,
    @SerializedName("Published date")
    var published_date: String? = null,
    @SerializedName("Rate")
    @Expose
    val Rate: String? = null,
    @SerializedName("photo")
    @Expose
    val photo: String? = null
)
