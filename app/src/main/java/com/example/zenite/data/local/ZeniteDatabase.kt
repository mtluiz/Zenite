package com.example.zenite.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.zenite.data.local.converter.Converters
import com.example.zenite.data.local.dao.AnswerDao
import com.example.zenite.data.local.dao.MoodDao
import com.example.zenite.data.local.dao.QuestionnaireDao
import com.example.zenite.data.local.dao.UserDao
import com.example.zenite.data.local.entity.AnswerEntity
import com.example.zenite.data.local.entity.MoodEntity
import com.example.zenite.data.local.entity.QuestionnaireEntity
import com.example.zenite.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        MoodEntity::class,
        QuestionnaireEntity::class,
        AnswerEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ZeniteDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun moodDao(): MoodDao
    abstract fun questionnaireDao(): QuestionnaireDao
    abstract fun answerDao(): AnswerDao

    companion object {
        @Volatile
        private var INSTANCE: ZeniteDatabase? = null

        fun getDatabase(context: Context): ZeniteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ZeniteDatabase::class.java,
                    "zenite_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 