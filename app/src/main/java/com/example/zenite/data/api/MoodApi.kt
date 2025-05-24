package com.example.zenite.data.api

import com.example.zenite.data.api.model.MoodRequest
import com.example.zenite.data.api.model.MoodResponse
import com.example.zenite.data.api.model.MoodsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MoodApi {
    @POST("moods")
    suspend fun addMood(@Body request: MoodRequest): Response<MoodResponse>
    
    @GET("moods/{code}")
    suspend fun getMoods(
        @Path("code") code: String,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): Response<MoodsResponse>
} 