package com.towhid.hadis.network.model.response.hadis_details

data class HadisDetailRes(
    val collection : String,
    val bookNumber : String,
    val hadithNumber : String,
    val hadith : List<Hadith>
)