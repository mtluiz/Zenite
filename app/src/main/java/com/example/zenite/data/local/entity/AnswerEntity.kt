package com.example.zenite.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.zenite.data.local.converter.Converters

@Entity(
    tableName = "question_answers",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["code"],
            childColumns = ["userCode"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userCode"), Index("questionnaireId")]
)
@TypeConverters(Converters::class)
data class AnswerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val remoteId: String? = null, // ID from the API
    val userCode: String,
    val questionnaireId: String,
    val answers: List<AnswerItemEntity>,
    val submittedAt: String,
    val isSynced: Boolean = false // Whether this submission has been synced with the server
)

data class AnswerItemEntity(
    val questionId: String,
    val question: String,
    val answer: String
) 