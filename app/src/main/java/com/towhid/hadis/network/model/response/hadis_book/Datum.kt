package com.towhid.hadis.network.model.response.hadis_book

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Datum(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val hasBooks: Boolean,
    val hasChapters: Boolean,
    val collection: List<Collection>,
    val totalHadith: Int,
    val totalAvailableHadith: Int
): Parcelable