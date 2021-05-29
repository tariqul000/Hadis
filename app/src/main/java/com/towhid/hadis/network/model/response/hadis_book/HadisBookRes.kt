package com.towhid.hadis.network.model.response.hadis_book

import com.beust.klaxon.Klaxon

private val klaxon = Klaxon()

data class HadisBookRes(
    val total: Int,
    val limit: Int,
    val previous: Int?,
    val next: Int?,
    val data: List<Datum>
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<HadisBookRes>(json)
    }
}