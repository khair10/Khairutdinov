package com.khair.tinkofflabapp.data.remote

import com.google.gson.annotations.SerializedName

class GifPostWrapper(
    @SerializedName("result")
    val result: List<GifPost> = ArrayList()
) {
}