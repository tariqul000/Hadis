package com.towhid.hadis.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.towhid.hadis.network.model.response.hadis_book.Datum

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(list:List<Datum>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookSingle(datum: Datum)

    @Query("SELECT * FROM Datum")
    fun getAllBooks():PagingSource<Int,Datum>

    @Query("DELETE FROM Datum")
    suspend fun deleteAllBooks()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(list:List<BookRemoteKey>)

    @Query("SELECT * FROM BookRemoteKey WHERE id = :id")
    suspend fun getAllRemoteKey(id:String):BookRemoteKey?

    @Query("DELETE FROM BookRemoteKey")
    suspend fun deleteAllRemoteKeys()


}