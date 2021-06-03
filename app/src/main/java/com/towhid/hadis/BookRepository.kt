package com.towhid.hadis

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.towhid.hadis.network.api.BookInterface
import com.towhid.hadis.network.model.response.hadis_book.Datum
import com.towhid.hadis.paging.BookPagingSource

class BookRepository(var bookInterface:BookInterface) {

    fun getAllBookStream(): LiveData<PagingData<Datum>> = Pager(
    config = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        enablePlaceholders = false
    ),
        pagingSourceFactory = {
            BookPagingSource(bookInterface)
        }
    ).liveData

}