package com.towhid.hadis.network.model.response.hadis_chapter

data class Datum(
    val bookNumber: String,
    val book: List<Book>,
    val hadithStartNumber: Int,
    val hadithEndNumber: Int,
    val numberOfHadith: Int
)