package com.mocat.btb.network.api

import com.towhid.hadis.ServerInfo
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {
        private var mInstance: RetrofitClient? = null

        private var retrofit: Retrofit? = null

        @Synchronized
        fun getInstance(): RetrofitClient {
            if (mInstance == null)
                mInstance =
                    RetrofitClient()
            return mInstance as RetrofitClient
        }
    }

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Content-Type", "application/json")
                    it.addHeader("X-API-Key", "SqD712P3E82xnwOAEOkGd5JZH8s9wRR24TqNFzjk")
                }.build())
            }
            .connectTimeout(30, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.MINUTES)
            .build()
        if (retrofit == null)
            retrofit = Retrofit.Builder()
                .baseUrl(ServerInfo.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()


    }
    fun getApi(): Api {
        return retrofit!!.create(Api::class.java)
    }

    fun getRetrofit(): Retrofit {
        return retrofit!!
    }


}