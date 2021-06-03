package com.towhid.hadis.paging

import androidx.paging.PagingSource
import com.towhid.hadis.network.api.BookInterface
import com.towhid.hadis.network.model.response.hadis_book.Datum
import retrofit2.HttpException
import java.io.IOException

const val STARTING_INDEX = 1

class BookPagingSource(var bookInterface: BookInterface) : PagingSource<Int, Datum>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Datum> {

        val position = params.key ?: STARTING_INDEX

        return try {
            val data =bookInterface.HadisBookRes_(position,20)
            LoadResult.Page(
                data=data.data,
                prevKey = data.previous,
                nextKey = data.next
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }


    }
}