package com.example.testwithpoetry.di

import android.content.Context
import androidx.room.Room
import com.example.testwithpoetry.data.poetry.local.AuthorDao
import com.example.testwithpoetry.data.configs.AppDatabase
import com.example.testwithpoetry.data.user.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesHttClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

    @Provides
    @Singleton
    fun providesUserTokenRoomDatabase(@ApplicationContext app: Context): AppDatabase =
        Room.databaseBuilder(
            app, AppDatabase::class.java, "app_database"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    @Singleton
    fun providesAuthorDao(appDatabase: AppDatabase): AuthorDao = appDatabase.authorDao()

}