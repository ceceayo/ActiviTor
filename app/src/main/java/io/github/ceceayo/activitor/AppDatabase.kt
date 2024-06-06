package io.github.ceceayo.activitor

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.ceceayo.activitor.schemas.Post
import io.github.ceceayo.activitor.schemas.PostDao

@Database(entities = [Post::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): PostDao
}
