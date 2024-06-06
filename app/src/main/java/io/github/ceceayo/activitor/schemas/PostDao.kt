package io.github.ceceayo.activitor.schemas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {
    @Query("SELECT * FROM Post")
    fun getAll(): List<Post>

    @Query("SELECT * FROM post WHERE id IN (:userIds)")
    fun loadAllByIds(postIds: IntArray): List<Post>

    @Insert
    fun insertAll(vararg posts: Post)

    @Delete
    fun delete(post: Post)
}
