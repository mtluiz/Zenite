package com.example.zenite.data.local.converter

import androidx.room.TypeConverter
import com.example.zenite.data.local.entity.AnswerItemEntity
import com.example.zenite.data.local.entity.QuestionEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromQuestionList(questions: List<QuestionEntity>?): String? {
        return questions?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toQuestionList(questionsString: String?): List<QuestionEntity>? {
        if (questionsString == null) return null
        val type = object : TypeToken<List<QuestionEntity>>() {}.type
        return gson.fromJson(questionsString, type)
    }

    @TypeConverter
    fun fromStringList(strings: List<String>?): String? {
        return strings?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toStringList(stringListString: String?): List<String>? {
        if (stringListString == null) return null
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(stringListString, type)
    }
    
    @TypeConverter
    fun fromAnswerItemList(answers: List<AnswerItemEntity>?): String? {
        return answers?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toAnswerItemList(answersString: String?): List<AnswerItemEntity>? {
        if (answersString == null) return null
        val type = object : TypeToken<List<AnswerItemEntity>>() {}.type
        return gson.fromJson(answersString, type)
    }
} 