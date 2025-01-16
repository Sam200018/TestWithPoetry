package com.example.testwithpoetry.data.configs

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testwithpoetry.data.poetry.local.AuthorDao
import com.example.testwithpoetry.data.user.local.UserDao
import com.example.testwithpoetry.localModels.Author
import com.example.testwithpoetry.localModels.User

@Database(entities = [User::class, Author::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun authorDao(): AuthorDao
}