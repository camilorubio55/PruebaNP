package com.example.ingresonapoleon.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ingresonapoleon.model.data.PostDB
import io.reactivex.Completable

@Dao
interface PostDao {

    @Query("SELECT * FROM post")
    fun getPostsDB(): List<PostDB>

    @Query("SELECT * FROM post WHERE isFavorite = 1")
    fun getPostsFavDB(): List<PostDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDB(posts: List<PostDB>)

    @Query("DELETE FROM post")
    fun deleteAllPostDB(): Completable

    @Query("DELETE FROM post WHERE id = :id")
    fun deletePostDB(id: Int): Completable

    @Query("UPDATE post SET isRead = :isRead WHERE id = :id")
    fun readPostDB(id: Int, isRead: Int): Completable

    @Query("UPDATE post SET isFavorite = :isFavorite WHERE id = :id")
    fun isFavoritePostDB(id: Int, isFavorite: Int): Completable

}