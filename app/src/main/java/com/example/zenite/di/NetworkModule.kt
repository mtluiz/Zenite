package com.example.zenite.di

import android.content.Context
import com.example.zenite.data.api.AuthApi
import com.example.zenite.data.api.MoodApi
import com.example.zenite.data.api.QuestionnaireApi
import com.example.zenite.data.local.ZeniteDatabase
import com.example.zenite.data.local.dao.MoodDao
import com.example.zenite.data.local.dao.QuestionnaireDao
import com.example.zenite.data.local.dao.UserDao
import com.example.zenite.data.local.dao.AnswerDao
import com.example.zenite.data.repository.MoodRepository
import com.example.zenite.data.repository.UserRepository
import com.example.zenite.data.repository.QuestionnaireRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://zenit-api-node-production.up.railway.app/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoodApi(retrofit: Retrofit): MoodApi {
        return retrofit.create(MoodApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQuestionnaireApi(retrofit: Retrofit): QuestionnaireApi {
        return retrofit.create(QuestionnaireApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ZeniteDatabase {
        return ZeniteDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: ZeniteDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideMoodDao(database: ZeniteDatabase): MoodDao {
        return database.moodDao()
    }

    @Provides
    @Singleton
    fun provideQuestionnaireDao(database: ZeniteDatabase): QuestionnaireDao {
        return database.questionnaireDao()
    }

    @Provides
    @Singleton
    fun provideAnswerDao(database: ZeniteDatabase): AnswerDao {
        return database.answerDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao, authApi: AuthApi): UserRepository {
        return UserRepository(userDao, authApi)
    }

    @Provides
    @Singleton
    fun provideMoodRepository(moodDao: MoodDao, moodApi: MoodApi): MoodRepository {
        return MoodRepository(moodDao, moodApi)
    }

    @Provides
    @Singleton
    fun provideQuestionnaireRepository(
        questionnaireDao: QuestionnaireDao,
        answerDao: AnswerDao,
        questionnaireApi: QuestionnaireApi
    ): QuestionnaireRepository {
        return QuestionnaireRepository(questionnaireDao, answerDao, questionnaireApi)
    }
} 