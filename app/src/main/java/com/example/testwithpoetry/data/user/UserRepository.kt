package com.example.testwithpoetry.data.user

import com.example.testwithpoetry.data.user.local.UserDao
import com.example.testwithpoetry.localModels.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun getUserInfo(): Result<User?> {
        return withContext(Dispatchers.IO){
            try {
                Result.success(userDao.getFirst())
            } catch (e: IOException) {
                Result.failure(e)
            }
        }
    }

    suspend fun insertUserInfo(user: User): Result<String> {
        return try {
            userDao.insertUser(user)
            Result.success("Usurio insertado")
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}