package com.example.zenite.data.repository

import com.example.zenite.data.api.QuestionnaireApi
import com.example.zenite.data.api.model.AnswerData
import com.example.zenite.data.api.model.AnswerRequest
import com.example.zenite.data.api.model.AnswerWithQuestionData
import com.example.zenite.data.local.dao.AnswerDao
import com.example.zenite.data.local.dao.QuestionnaireDao
import com.example.zenite.data.local.entity.AnswerEntity
import com.example.zenite.data.local.entity.AnswerItemEntity
import com.example.zenite.data.local.entity.QuestionEntity
import com.example.zenite.data.local.entity.QuestionnaireEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionnaireRepository @Inject constructor(
    private val questionnaireDao: QuestionnaireDao,
    private val answerDao: AnswerDao,
    private val questionnaireApi: QuestionnaireApi
) {
    
    
    suspend fun loadQuestionnaires(): Result<List<QuestionnaireEntity>> = withContext(Dispatchers.IO) {
        try {
         
            val response = questionnaireApi.getAllQuestionnaires()
            
            if (response.isSuccessful && response.body() != null) {
             
                val questionnaires = response.body()!!.questionnaires.map { questionnaireData ->
                    QuestionnaireEntity(
                        id = questionnaireData.id,
                        title = questionnaireData.title,
                        description = questionnaireData.description ?: "",
                        questions = questionnaireData.questions.map { questionData ->
                            QuestionEntity(
                                id = questionData.id,
                                text = questionData.text,
                                type = questionData.type ?: "text",
                                options = questionData.options
                            )
                        }
                    )
                }
                
                questionnaireDao.insertQuestionnaires(questionnaires)
                return@withContext Result.success(questionnaires)
            } else {
               
                val localQuestionnaires = questionnaireDao.observeAllQuestionnaires().first()
                if (localQuestionnaires.isNotEmpty()) {
                    return@withContext Result.success(localQuestionnaires)
                } else {
                    return@withContext Result.failure(Exception("Não foi possível carregar os questionários"))
                }
            }
        } catch (e: Exception) {
            
            try {
                val localQuestionnaires = questionnaireDao.observeAllQuestionnaires().first()
                if (localQuestionnaires.isNotEmpty()) {
                    return@withContext Result.success(localQuestionnaires)
                } else {
                    return@withContext Result.failure(e)
                }
            } catch (e2: Exception) {
                return@withContext Result.failure(e2)
            }
        }
    }
    

    suspend fun getQuestionnaire(id: String): Result<QuestionnaireEntity> = withContext(Dispatchers.IO) {
        try {

            val response = questionnaireApi.getQuestionnaire(id)
            
            if (response.isSuccessful && response.body() != null) {
                val questionnaireData = response.body()!!.questionnaire
                val questionnaireEntity = QuestionnaireEntity(
                    id = questionnaireData.id,
                    title = questionnaireData.title,
                    description = questionnaireData.description ?: "",
                    questions = questionnaireData.questions.map { questionData ->
                        QuestionEntity(
                            id = questionData.id,
                            text = questionData.text,
                            type = questionData.type ?: "text",
                            options = questionData.options
                        )
                    }
                )
                
                questionnaireDao.insertQuestionnaire(questionnaireEntity)
                return@withContext Result.success(questionnaireEntity)
            } else {

                val localQuestionnaire = questionnaireDao.getQuestionnaireById(id)
                if (localQuestionnaire != null) {
                    return@withContext Result.success(localQuestionnaire)
                } else {
                    return@withContext Result.failure(Exception("Questionário não encontrado"))
                }
            }
        } catch (e: Exception) {

            try {
                val localQuestionnaire = questionnaireDao.getQuestionnaireById(id)
                if (localQuestionnaire != null) {
                    return@withContext Result.success(localQuestionnaire)
                } else {
                    return@withContext Result.failure(e)
                }
            } catch (e2: Exception) {
                return@withContext Result.failure(e2)
            }
        }
    }
    

    suspend fun submitAnswers(
        userCode: String,
        questionnaireId: String,
        answers: List<Pair<String, String>> 
    ): Result<AnswerEntity> = withContext(Dispatchers.IO) {
        try {
            val timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).format(Date())
            

            val questionnaire = try {
                val result = getQuestionnaire(questionnaireId)
                result.getOrNull()
            } catch (e: Exception) {
                null
            }
            
            val answerItems = answers.map { (questionId, answer) ->
                val questionText = questionnaire?.questions?.find { it.id == questionId }?.text ?: ""
                AnswerItemEntity(
                    questionId = questionId,
                    question = questionText,
                    answer = answer
                )
            }
            
            val answerEntity = AnswerEntity(
                userCode = userCode,
                questionnaireId = questionnaireId,
                answers = answerItems,
                submittedAt = timestamp,
                isSynced = false
            )
            
            val id = answerDao.insertAnswer(answerEntity)
            val insertedAnswer = answerEntity.copy(id = id)
            
            try {
                val apiAnswers = answers.map { (questionId, answer) ->
                    AnswerData(
                        questionId = questionId,
                        answer = answer
                    )
                }
                
                val request = AnswerRequest(
                    code = userCode,
                    answers = apiAnswers
                )
                
                val response = questionnaireApi.submitAnswers(questionnaireId, request)
                
                if (response.isSuccessful && response.body() != null) {
                    val remoteId = response.body()!!.submission.id
                    answerDao.markAsSynced(id, remoteId)
                    return@withContext Result.success(insertedAnswer.copy(remoteId = remoteId, isSynced = true))
                }
            } catch (e: Exception) {
               
            }
            
            return@withContext Result.success(insertedAnswer)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }
    
   
    fun observeQuestionnaires(): Flow<List<QuestionnaireEntity>> {
        return questionnaireDao.observeAllQuestionnaires()
    }
    
   
    fun observeUserAnswers(userCode: String): Flow<List<AnswerEntity>> {
        return answerDao.observeAnswersByUserCode(userCode)
    }
} 