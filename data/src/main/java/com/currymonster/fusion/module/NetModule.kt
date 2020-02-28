package com.currymonster.fusion.module

import android.content.Context
import com.currymonster.fusion.interceptors.AuthInterceptor
import com.currymonster.fusion.interceptors.FusionRxJava2CallAdapterFactory
import com.currymonster.fusion.net.ReviewService
import com.currymonster.fusion.net.SearchService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetModule {

    @Provides
    @SessionScope
    fun provideAuthInterceptor(envProvider: EnvProvider) = AuthInterceptor(envProvider)

    @Provides
    @SessionScope
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @SessionScope
    fun provideHttpClient(
        context: Context,
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val cache = Cache(context.cacheDir, MAXIMUM_HTTP_CACHE_SIZE)

        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .build()
    }

    @Provides
    @SessionScope
    fun provideRetrofit(
        context: Context,
        okHttpClient: OkHttpClient,
        envProvider: EnvProvider
    ): Retrofit {
        return Retrofit.Builder().baseUrl(envProvider.apiBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .addCallAdapterFactory(FusionRxJava2CallAdapterFactory.create(context))
            .build()
    }

    @Provides
    @SessionScope
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Provides
    @SessionScope
    fun provideReviewService(retrofit: Retrofit): ReviewService =
        retrofit.create(ReviewService::class.java)

    companion object {
        const val MAXIMUM_HTTP_CACHE_SIZE: Long = 50 * 1024 * 1024 // 50MB
    }
}