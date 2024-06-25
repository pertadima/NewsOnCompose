package com.codingle.core_network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CommonHeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().newBuilder().build())
    }
}