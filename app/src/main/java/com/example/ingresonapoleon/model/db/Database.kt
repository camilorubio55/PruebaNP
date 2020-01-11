package com.example.ingresonapoleon.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ingresonapoleon.model.data.PostDB

@Database(entities = [PostDB::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}