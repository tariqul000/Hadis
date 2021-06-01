package com.towhid.hadis.network.model.response.hadis_details

data class Hadith (
	val lang : String,
	val chapterNumber : String,
	val chapterTitle : String,
	val body : String,
	val grades : List<Grades>
)