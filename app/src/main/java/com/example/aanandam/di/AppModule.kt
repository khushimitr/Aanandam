package com.example.aanandam.di

import android.content.Context
import com.example.aanandam.model.network.AanandamAPI
import com.example.aanandam.model.repository.AanandamRepoImpl
import com.example.aanandam.model.repository.AanandamRepository
import com.example.aanandam.utils.Constants
import com.example.aanandam.utils.SessionManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideSessionManager(
        @ApplicationContext context : Context
    ) = SessionManager(context)

    @Singleton
    @Provides
    fun provideAanandamAPI() : AanandamAPI {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AanandamAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideAanandamRepository(
        aanandamAPI: AanandamAPI,
        sessionManager: SessionManager
    ): AanandamRepository {
        return AanandamRepoImpl(
            aanandamAPI, sessionManager
        )
    }
}