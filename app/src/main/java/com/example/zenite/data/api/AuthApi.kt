package com.example.zenite.data.api

import com.example.zenite.data.api.model.LoginRequest
import com.example.zenite.data.api.model.LoginResponse
import com.example.zenite.data.api.model.RegisterRequest
import com.example.zenite.data.api.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
} 