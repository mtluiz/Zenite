package com.example.zenite.data.repository

import com.example.zenite.data.api.MoodApi
import com.example.zenite.data.api.model.MoodData
import com.example.zenite.data.api.model.MoodRequest
import com.example.zenite.data.local.dao.MoodDao
import com.example.zenite.data.local.entity.MoodEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoodRepository @Inject constructor(
    private val moodDao: MoodDao,
    private val moodApi: MoodApi
) {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    
    fun observeMoodsByUserCode(userCode: String): Flow<List<MoodEntity>> {
        return moodDao.observeMoodsByUserCode(userCode)
    }
    
    fun observeMoodsByUserCodeAndDateRange(
        userCode: String,
        startDate: String,
        endDate: String
    ): Flow<List<MoodEntity>> {
        return moodDao.observeMoodsByUserCodeAndDateRange(userCode, startDate, endDate)
    }
    
    suspend fun addMood(
        userCode: String,
        mood: String,
        description: String? = null,
        date: String? = null
    ): Result<MoodEntity> = withContext(Dispatchers.IO) {
        try {
            val currentDate = date ?: dateFormat.format(Date())
            val timestamp = System.currentTimeMillis().toString()
            
            val moodEntity = MoodEntity(
                userCode = userCode,
                mood = mood,
                description = description,
                date = currentDate,
                createdAt = timestamp,
                isSynced = false
            )
            
            val id = moodDao.insertMood(moodEntity)
            val insertedMood = moodEntity.copy(id = id)
            
            try {
                val response = moodApi.addMood(
                    MoodRequest(
                        code = userCode,
                        mood = mood,
                        description = description,
                        date = currentDate
                    )
                )
                
                if (response.isSuccessful && response.body() != null) {
                    val remoteId = response.body()!!.mood.id
                    moodDao.markAsSynced(id, remoteId)
                    return@withContext Result.success(insertedMood.copy(remoteId = remoteId, isSynced = true))
                }
            } catch (e: Exception) {
            }
            
            return@withContext Result.success(insertedMood)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }
    
    suspend fun syncMoods(userCode: String): Result<Int> = withContext(Dispatchers.IO) {
        try {
            val unsyncedMoods = moodDao.getUnsyncedMoods()
            var syncedCount = 0
            
            unsyncedMoods.forEach { moodEntity ->
                try {
                    val response = moodApi.addMood(
                        MoodRequest(
                            code = moodEntity.userCode,
                            mood = moodEntity.mood,
                            description = moodEntity.description,
                            date = moodEntity.date
                        )
                    )
                    
                    if (response.isSuccessful && response.body() != null) {
                        val remoteId = response.body()!!.mood.id
                        moodDao.markAsSynced(moodEntity.id, remoteId)
                        syncedCount++
                    }
                } catch (e: Exception) {
                   
                }
            }
            
            try {
                val response = moodApi.getMoods(userCode)
                
                if (response.isSuccessful && response.body() != null) {
                    val remoteMoods = response.body()!!.moods
                    
                    remoteMoods.forEach { remoteMood ->
                        importRemoteMood(userCode, remoteMood)
                    }
                }
            } catch (e: Exception) {
            }
            
            return@withContext Result.success(syncedCount)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }
    
    private suspend fun importRemoteMood(userCode: String, remoteMood: MoodData) {
        // Check if this mood already exists locally with the same remoteId
        val existingMoods = moodDao.observeMoodsByUserCode(userCode)
            .firstOrNull() ?: emptyList()
        
        val matchingMoods = existingMoods.filter { it.remoteId == remoteMood.id }
        
        if (matchingMoods.isEmpty()) {
            // Create a new mood entity from the remote data
            val moodEntity = MoodEntity(
                remoteId = remoteMood.id,
                userCode = userCode,
                mood = remoteMood.mood,
                description = remoteMood.description,
                date = remoteMood.date,
                createdAt = remoteMood.createdAt,
                isSynced = true
            )
            
            moodDao.insertMood(moodEntity)
        }
    }
} 