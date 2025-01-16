package com.example.testwithpoetry.data.poetry

import com.example.testwithpoetry.data.NetworkResource
import com.example.testwithpoetry.data.poetry.local.AuthorDao
import com.example.testwithpoetry.localModels.Author
import com.example.testwithpoetry.remoteResponses.AuthorsResponse
import com.example.testwithpoetry.remoteResponses.PoemResponse
import com.example.testwithpoetry.remoteResponses.PoemTitleReponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class PoetryRepository @Inject constructor(
    private val client: HttpClient,
    private val authorDao: AuthorDao
) {
    suspend fun getAuths(): NetworkResource<AuthorsResponse> {
        return withContext(Dispatchers.IO) {
            val response = client.get("https://poetrydb.org/author")

            if (response.status.value == 200) {
                NetworkResource.Success(response.body())
            } else {
                NetworkResource.Fail(response.status.description)
            }
        }
    }

    suspend fun getTitlesByAuthor(authorName: String): NetworkResource<List<PoemTitleReponse>> {
        return withContext(Dispatchers.IO) {
            val response = client.get("https://poetrydb.org/author/$authorName/title")
            if (response.status.value == 200) {
                NetworkResource.Success(response.body())
            } else {
                NetworkResource.Fail(response.status.description)
            }
        }
    }

    suspend fun getPoem(authorName: String, title: String): NetworkResource<List<PoemResponse>> {
        return withContext(Dispatchers.IO) {
            val response = client.get("https://poetrydb.org/author,title/$authorName;$title")

            if (response.status.value == 200) {
                delay(3000L) // This is the only code line that you can't change
                NetworkResource.Success(response.body())
            } else {
                NetworkResource.Fail(response.status.description)
            }
        }
    }

    suspend fun getAuthorByNameLocal(authorName: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val author = authorDao.getAuthorByName(authorName)
                if (author != null) Result.success(true)
                else Result.failure(Throwable("Not found"))
            } catch (e: IOException) {
                Result.failure(e)
            }
        }
    }

    suspend fun insertAuthorLocal(author: Author): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                authorDao.insertAuthor(author)
                Result.success("Author added")
            } catch (e: IOException) {
                Result.failure(e)
            }
        }
    }

    suspend fun deleteAuthorLocal(authorName: String):Result<Boolean>{
        return  withContext(Dispatchers.IO){
            try {
                authorDao.deleteAuthorByName(authorName)
                Result.success(true)
            }catch (e:IOException){
                Result.failure(e)
            }
        }
    }
}