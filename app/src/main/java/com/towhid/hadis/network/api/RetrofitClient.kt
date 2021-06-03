package com.towhid.hadis.network.api

import com.towhid.hadis.Constants.API_KEY
import com.towhid.hadis.ServerInfo
import com.towhid.hadis.application.Hadis
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


class RetrofitClient {

    companion object {
        private var mInstance: RetrofitClient? = null

        private var retrofit: Retrofit? = null

        private var HEADER_CACHE_CONTROL = "Cache-Control"
        private var HEADER_PRAGMA = "Pragma"


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
            .addInterceptor(provideOfflineCacheInterceptor())
            .addNetworkInterceptor(provideCacheInterceptor())
            .cache(provideCache())
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


    fun provideCache(): Cache {
        var cache: Cache? = null;
        try {
            cache = Cache(
                File(Hadis.appContext.getCacheDir(), "http-cache"),
                20 * 1024 * 1024
            ); // 20 MB
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return cache!!
    }


    private fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val response: Response = chain.proceed(chain.request())
            val cacheControl: CacheControl
            cacheControl = if (isConnected()) {
                CacheControl.Builder()
                    .maxAge(5, TimeUnit.SECONDS)
                    .build()
            } else {
                CacheControl.Builder()
                    .maxStale(365, TimeUnit.DAYS)
                    .build()
            }
            response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }


    private fun provideOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->

            var request: Request = chain.request().newBuilder().also {
                it.addHeader("Content-Type", "application/json")
                it.addHeader("X-API-Key", API_KEY)
            }.build()

            if (!isConnected()) {
                val cacheControl: CacheControl = CacheControl.Builder()
                    .maxStale(365, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }
    }

    @Throws(InterruptedException::class, IOException::class)
    fun isConnected(): Boolean {
        val command = "ping -c 1 google.com"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    }

}
