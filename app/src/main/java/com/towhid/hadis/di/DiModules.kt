package com.towhid.hadis.di

import android.content.Context
import com.towhid.hadis.BookRepository
import com.towhid.hadis.Constants.API_KEY
import com.towhid.hadis.ServerInfo.BASE_URL
import com.towhid.hadis.network.api.BookInterface
import com.towhid.hadis.room.BookDao
import com.towhid.hadis.room.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModules {


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .client(OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Content-Type", "application/json")
                    it.addHeader("X-API-Key", API_KEY)
                }.build())
            }
            .connectTimeout(30, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.MINUTES)
            .build())
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideBookInterface(retrofit: Retrofit): BookInterface =
        retrofit.create(BookInterface::class.java)


    @Singleton
    @Provides
    fun provideRepository(bookInterface: BookInterface): BookRepository =
        BookRepository(bookInterface)


    @Singleton
    @Provides
    fun provideBookDatabase(@ApplicationContext context: Context): BookDatabase {
        return BookDatabase.getInstance(context)
    }


    @Singleton
    @Provides
    fun provideBookDao(bookDatabase: BookDatabase): BookDao {
        return bookDatabase.getBookDao()
    }


}