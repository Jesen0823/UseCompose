package com.jesen.driverexampaging.net

import com.jesen.driverexampaging.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    const val APP_KEY = "647fd7f08ee823199d919a2a09161345"

    private val instance: Retrofit by lazy {

        val logInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            //显示日志
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val okhttpClient = OkHttpClient.Builder().addInterceptor(logInterceptor)
            .connectTimeout(5, TimeUnit.SECONDS)//设置超时时间
            .retryOnConnectionFailure(true).build()

        Retrofit.Builder()
            .client(okhttpClient)
            .baseUrl("https://way.jd.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createApi(clazz: Class<T>): T {
        return instance.create(clazz) as T
    }
}