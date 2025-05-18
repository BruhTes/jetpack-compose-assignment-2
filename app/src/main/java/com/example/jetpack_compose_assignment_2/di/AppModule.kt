package com.example.jetpack_compose_assignment_2.di

import android.content.Context
import androidx.room.Room
import com.example.jetpack_compose_assignment_2.data.TodoRepository
import com.example.jetpack_compose_assignment_2.data.local.AppDatabase
import com.example.jetpack_compose_assignment_2.data.local.TodoDao
import com.example.jetpack_compose_assignment_2.data.remote.TodoApi
import com.example.jetpack_compose_assignment_2.domain.TodoRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "todos_db"
        ).build()

    @Provides
    fun provideTodoDao(db: AppDatabase): TodoDao = db.todoDao()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideTodoApi(retrofit: Retrofit): TodoApi = retrofit.create(TodoApi::class.java)

    @Provides
    @Singleton
    fun provideTodoRepository(
        api: TodoApi,
        dao: TodoDao
    ): TodoRepositoryInterface = TodoRepository(api, dao)
} 