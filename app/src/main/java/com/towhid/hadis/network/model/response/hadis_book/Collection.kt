package com.towhid.hadis.network.model.response.hadis_book

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Collection(
    val lang: String,
    val title: String,
    val shortIntro: String
): Parcelable