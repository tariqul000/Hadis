package com.mocat.btb.network.api

import com.towhid.hadis.network.model.response.hadis_book.HadisBookRes

import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("collections")
    fun HadisBookRes_(
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? =50
    ): Call<HadisBookRes>

}


