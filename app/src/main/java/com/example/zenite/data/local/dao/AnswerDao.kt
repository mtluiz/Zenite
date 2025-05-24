package com.example.zenite.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zenite.data.local.entity.AnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: AnswerEntity): Long
    
    @Update
    suspend fun updateAnswer(answer: AnswerEntity)
    
    @Query("SELECT * FROM question_answers WHERE userCode = :userCode ORDER BY submittedAt DESC")
    fun observeAnswersByUserCode(userCode: String): Flow<List<AnswerEntity>>
    
    @Query("SELECT * FROM question_answers WHERE userCode = :userCode AND questionnaireId = :questionnaireId")
    suspend fun getAnswersForQuestionnaire(userCode: String, questionnaireId: String): List<AnswerEntity>
    
    @Query("SELECT * FROM question_answers WHERE isSynced = 0")
    suspend fun getUnsyncedAnswers(): List<AnswerEntity>
    
    @Query("UPDATE question_answers SET remoteId = :remoteId, isSynced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long, remoteId: String)
} 