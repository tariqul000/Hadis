package com.towhid.hadis.network.model.response.hadis_list

data class HadisListRes(
    val total: Int,
    val limit: Int,
    val previous: Int?,
    val next: Int?,
    val data: List<Datum>
)