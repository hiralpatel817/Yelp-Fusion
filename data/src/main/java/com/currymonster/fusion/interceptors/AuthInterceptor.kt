package com.currymonster.fusion.interceptors

import com.currymonster.fusion.module.EnvProvider
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val envProvider: EnvProvider
) : Interceptor {

    /*
     * Intercept and add Auth token (api key) to each request
     */

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer ${envProvider.apiKey}")
            .build()

        return chain.proceed(request)
    }
}