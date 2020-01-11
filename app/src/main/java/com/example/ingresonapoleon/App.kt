package com.example.ingresonapoleon

import android.app.Application
import androidx.room.Room
import com.example.ingresonapoleon.model.PostRepository
import com.example.ingresonapoleon.model.UserRepository
import com.example.ingresonapoleon.model.db.AppDatabase
import com.example.ingresonapoleon.services.Connection
import com.example.ingresonapoleon.viewmodel.PostViewModel
import com.example.ingresonapoleon.viewmodel.UserViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class App: Application() {

    companion object {

        // Connection
        private lateinit var connection: Connection
        lateinit var client : OkHttpClient

        // BD
        private lateinit var appDatabase: AppDatabase

        // Users
        private lateinit var userViewModel: UserViewModel
        private lateinit var userRepository: UserRepository

        // Posts
        private lateinit var postViewModel: PostViewModel
        private lateinit var postRepository: PostRepository

        // Injection
        fun injectUserViewModel() = userViewModel
        fun injectPostViewModel() = postViewModel
        fun injectPostDao() = appDatabase.postDao()
    }

    override fun onCreate() {
        super.onCreate()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()

        // Connection
        connection = Connection

        // BD
        appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").allowMainThreadQueries().build()

        // Users
        userRepository = UserRepository(connection)
        userViewModel = UserViewModel(userRepository)

        // Posts
        postRepository = PostRepository(connection, injectPostDao())
        postViewModel = PostViewModel(postRepository)
    }
}