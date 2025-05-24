package com.example.zenite.data.api

import com.example.zenite.data.api.model.AnswerRequest
import com.example.zenite.data.api.model.AnswerSubmissionResponse
import com.example.zenite.data.api.model.QuestionnaireResponse
import com.example.zenite.data.api.model.QuestionnairesResponse
import com.example.zenite.data.api.model.UserAnswersResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuestionnaireApi {
    @GET("questionnaires")
    suspend fun getAllQuestionnaires(): Response<QuestionnairesResponse>
    
    @GET("questionnaires/{id}")
    suspend fun getQuestionnaire(@Path("id") id: String): Response<QuestionnaireResponse>
    
    @POST("questionnaires/{id}/answers")
    suspend fun submitAnswers(
        @Path("id") id: String,
        @Body request: AnswerRequest
    ): Response<AnswerSubmissionResponse>
    
    @GET("questionnaires/answers/{code}")
    suspend fun getUserAnswers(@Path("code") code: String): Response<UserAnswersResponse>
} 