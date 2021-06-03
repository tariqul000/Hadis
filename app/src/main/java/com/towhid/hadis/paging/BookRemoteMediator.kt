package com.towhid.hadis.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.towhid.hadis.network.api.BookInterface
import com.towhid.hadis.network.model.response.hadis_book.Datum
import com.towhid.hadis.room.BookDao
import com.towhid.hadis.room.BookRemoteKey
import java.io.InvalidObjectException

@ExperimentalPagingApi
class BookRemoteMediator(
    private val bookDao: BookDao,
    private val bookInterface: BookInterface,
    private val initialPage: Int = 1
) : RemoteMediator<Int, Datum>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Datum>
    ): MediatorResult {

        return try {

            // Judging the page number
            val page = when (loadType) {
                LoadType.APPEND -> {

                    val remoteKey =
                        getLastRemoteKey(state) ?: throw InvalidObjectException("Remote Key Not Found")
                    remoteKey.next ?: return MediatorResult.Success(endOfPaginationReached = true)

                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.REFRESH -> {
                    val remoteKey = getClosestRemoteKeys(state)
                    remoteKey?.next?.minus(1) ?: initialPage
                }
            }

            // make network request
            val response = bookInterface.AllHadisBookRes_(
                page,
                20
            )
            val endOfPagination = response.body()?.data?.size!! < state.config.pageSize

            if (response.isSuccessful) {

                response.body()?.let {

                    // flush our data
                    if (loadType == LoadType.REFRESH) {
                        bookDao.deleteAllBooks()
                        bookDao.deleteAllRemoteKeys()
                    }


                    val prev = if (page == initialPage) null else page - 1
                    val next = if (endOfPagination) null else page + 1


                    val list = response.body()?.data?.map {
                        BookRemoteKey(it.name, prev, next)
                    }


                    // make list of remote keys

                    if (list != null) {
                        bookDao.insertAllRemoteKeys(list)
                    }

                    // insert to the room
                    bookDao.insertBooks(it.data)


                }
                MediatorResult.Success(endOfPagination)
            } else {
                MediatorResult.Success(endOfPaginationReached = true)
            }


        } catch (e: Exception) {
            MediatorResult.Error(e)
        }

    }

    private suspend fun getClosestRemoteKeys(state: PagingState<Int, Datum>): BookRemoteKey? {

        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let {
                bookDao.getAllRemoteKey(it.name)
            }
        }

    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Datum>): BookRemoteKey? {
        return state.lastItemOrNull()?.let {
            bookDao.getAllRemoteKey(it.name)
        }
    }

}