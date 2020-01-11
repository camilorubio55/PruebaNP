package com.example.ingresonapoleon.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class PostDB(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "idUser")
    val idUser: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "body")
    val body: String,
    @ColumnInfo(name = "isRead")
    val isRead: Boolean,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean
)