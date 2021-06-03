package com.towhid.hadis.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.towhid.hadis.BookRepository
import com.towhid.hadis.listener.HadisClickListener
import com.towhid.hadis.model.HadisBook
import com.towhid.hadis.network.api.BookInterface
import com.towhid.hadis.network.model.response.hadis_book.Datum
import com.towhid.hadis.paging.BookRemoteMediator
import com.towhid.hadis.room.BookDao
import dagger.hilt.android.lifecycle.HiltViewModel
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltViewModel
class HadisBookViewModel @Inject constructor(
    private val bookRepository: BookRepository, private val bookDao: BookDao,
    private val bookInterface: BookInterface
) : ViewModel() {
    var list: LiveData<PagingData<Datum>> = bookRepository.getAllBookStream()

    @ExperimentalPagingApi
    val pager = Pager(
        PagingConfig(pageSize = 10),
        remoteMediator = BookRemoteMediator(bookDao, bookInterface, 1)
    ) {
        bookDao.getAllBooks()
    }.flow


    fun clickHadisBook(hadisBook: HadisBook) {
        Log.d("click","click")
        EventBus.getDefault().post(HadisClickListener(hadisBook))
    }


}