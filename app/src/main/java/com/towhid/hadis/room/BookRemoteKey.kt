package com.towhid.hadis.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id:String,
    val prev:Int?,
    val next:Int?
)