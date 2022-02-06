package com.khair.tinkofflabapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface GifPostService {

    @GET("random")
    suspend fun getRandom(): GifPost

    @GET("{category}/{page}")
    suspend fun getList(@Path("category") category: String, @Path("page") page: Int): GifPostWrapper
}