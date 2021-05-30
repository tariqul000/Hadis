package com.towhid.hadis.model

data class HadisChapter(
    var ChapterNumber: String,
    var ChapterNameEn: String,
    var ChapterNameAr: String,
    var hadithStartNumber: Int,
    var hadithEndNumber: Int,
    var numberOfHadith: Int
    )