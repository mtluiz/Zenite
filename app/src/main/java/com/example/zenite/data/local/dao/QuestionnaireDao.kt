package com.example.zenite.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zenite.data.local.entity.QuestionnaireEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionnaireDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionnaire(questionnaire: QuestionnaireEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionnaires(questionnaires: List<QuestionnaireEntity>)
    
    @Query("SELECT * FROM questionnaires")
    fun observeAllQuestionnaires(): Flow<List<QuestionnaireEntity>>
    
    @Query("SELECT * FROM questionnaires WHERE id = :id")
    suspend fun getQuestionnaireById(id: String): QuestionnaireEntity?
} 