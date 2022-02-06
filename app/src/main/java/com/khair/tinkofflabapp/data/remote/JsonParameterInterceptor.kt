package com.khair.tinkofflabapp.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class JsonParameterInterceptor: Interceptor {

    private val JSON_PARAM_NAME = "json"
    private val JSON_PARAM_VALUE = "true"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder().addQueryParameter(JSON_PARAM_NAME, JSON_PARAM_VALUE).build()
        val requestWithNewUrl = request.newBuilder().url(url).build()
        return chain.proceed(requestWithNewUrl)
    }
}