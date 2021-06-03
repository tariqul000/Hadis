package com.towhid.hadis.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.towhid.hadis.network.model.response.hadis_book.Datum

@Database(entities = [Datum::class,BookRemoteKey::class],version = 1)
@TypeConverters(Converters::class)
abstract class BookDatabase : RoomDatabase() {
    companion object{
        fun getInstance(context: Context):BookDatabase{
            return Room.databaseBuilder(context,BookDatabase::class.java,"name").build()
        }
    }
    abstract fun getBookDao():BookDao
}