package com.example.testwithpoetry.data.poetry.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testwithpoetry.localModels.Author

@Dao
interface AuthorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthor(author: Author)

    @Query("SELECT * from author WHERE name = :authorName")
    suspend fun getAuthorByName(authorName:String): Author?

    @Query("DELETE FROM author WHERE name = :authorName")
    suspend fun deleteAuthorByName(authorName: String)

}