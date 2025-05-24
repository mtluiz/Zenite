package com.example.zenite.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.zenite.data.local.converter.Converters

@Entity(tableName = "questionnaires")
@TypeConverters(Converters::class)
data class QuestionnaireEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val questions: List<QuestionEntity>
)

data class QuestionEntity(
    val id: String,
    val text: String,
    val type: String,
    val options: List<String>? = null
) 