package com.example.zenite.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "moods",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["code"],
            childColumns = ["userCode"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userCode")]
)
data class MoodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val remoteId: String? = null,
    val userCode: String,
    val mood: String,
    val description: String? = null,
    val date: String,
    val createdAt: String,
    val isSynced: Boolean = false
) 