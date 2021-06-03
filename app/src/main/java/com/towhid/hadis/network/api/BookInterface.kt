package com.towhid.hadis.network.api

import com.towhid.hadis.network.model.response.hadis_book.HadisBookRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookInterface {

    @GET("collections")
    suspend fun HadisBookRes_(
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? =50
    ): HadisBookRes

    @GET("collections")
    suspend fun AllHadisBookRes_(
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? =50
    ): Response<HadisBookRes>


}