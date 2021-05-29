package com.towhid.hadis.network.model.response.hadis_book

data class Datum(
    val name: String,
    val hasBooks: Boolean,
    val hasChapters: Boolean,
    val collection: List<Collection>,
    val totalHadith: Int,
    val totalAvailableHadith: Int
)