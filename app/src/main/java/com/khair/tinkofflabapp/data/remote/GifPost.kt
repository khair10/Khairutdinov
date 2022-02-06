package com.khair.tinkofflabapp.data.remote

import com.google.gson.annotations.SerializedName

class GifPost(
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("gifURL")
    val url: String = "",
    @SerializedName("description")
    val title: String = "",
    @SerializedName("author")
    val author: String = ""
) {
}