package com.example.zenite.data.api.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("password") val password: String
)

data class LoginRequest(
    @SerializedName("code") val code: String,
    @SerializedName("password") val password: String
)

data class RegisterResponse(
    @SerializedName("code") val code: String
)

data class LoginResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("code") val code: String
)

data class ErrorResponse(
    @SerializedName("error") val error: String
) 