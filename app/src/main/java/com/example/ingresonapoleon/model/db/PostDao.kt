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
    fun getPosts(): List<PostDB>

    @Query("SELECT * FROM post WHERE isFavorite = 1")
    fun getPostsFav(): List<PostDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<PostDB>)

    @Query("DELETE FROM post")
    fun deleteAllPost(): Completable

    @Query("DELETE FROM post WHERE id = :id")
    fun deletePost(id: Int): Completable

    @Query("UPDATE post SET isRead = 1 WHERE id = :id")
    fun readPostDB(id: Int)

/*    @Query("UPDATE post SET isFavorite = isFavorite WHERE id = :id")
    fun updateFavPost(id: Int, isFavorite: Int)*/

}