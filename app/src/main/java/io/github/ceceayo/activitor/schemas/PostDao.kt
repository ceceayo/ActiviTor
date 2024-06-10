package io.github.ceceayo.activitor.schemas

import android.annotation.SuppressLint
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.TimeZone
import java.util.UUID

@Dao
interface PostDao {
    @Query("SELECT * FROM Post")
    fun getAll(): List<Post>

    @Query("SELECT * FROM post WHERE id IN (:postIds)")
    fun loadAllByIds(postIds: IntArray): List<Post>

    @Insert
    fun insertAll(vararg posts: Post)

    @Delete
    fun delete(post: Post)

    @SuppressLint("SimpleDateFormat")
    fun createPost(data: String) {
        println("createPost called")
        val p = Post(
            id = UUID.randomUUID().toString(),
            content = data,
            created_at = LocalDateTime.now().toString()
        )
        println(p.toString())
        insertAll(p)
    }
}
