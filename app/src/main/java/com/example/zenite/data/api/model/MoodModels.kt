package com.example.zenite.data.api.model

import com.google.gson.annotations.SerializedName

data class MoodRequest(
    @SerializedName("code") val code: String,
    @SerializedName("mood") val mood: String,
    @SerializedName("description") val description: String? = null,
    @SerializedName("date") val date: String? = null // Format: YYYY-MM-DD
)

data class MoodResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("mood") val mood: MoodData
)

data class MoodsResponse(
    @SerializedName("moods") val moods: List<MoodData>
)

data class MoodData(
    @SerializedName("_id") val id: String,
    @SerializedName("code") val code: String,
    @SerializedName("mood") val mood: String,
    @SerializedName("description") val description: String?,
    @SerializedName("date") val date: String,
    @SerializedName("createdAt") val createdAt: String
) 