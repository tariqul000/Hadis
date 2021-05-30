package com.towhid.hadis.model

data class HadisBook(
    var no: Int,
    var name: String,
    var hasBooks: Boolean,
    var hasChapters: Boolean,
    var bookNameEn: String,
    var bookNameAr: String,
    var totalHadith: Int,
    var totalAvailableHadith: Int
    )