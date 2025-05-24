package com.example.zenite.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zenite.data.local.entity.MoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(mood: MoodEntity): Long
    
    @Update
    suspend fun updateMood(mood: MoodEntity)
    
    @Query("SELECT * FROM moods WHERE userCode = :userCode ORDER BY date DESC, createdAt DESC")
    fun observeMoodsByUserCode(userCode: String): Flow<List<MoodEntity>>
    
    @Query("SELECT * FROM moods WHERE userCode = :userCode AND date BETWEEN :startDate AND :endDate ORDER BY date DESC, createdAt DESC")
    fun observeMoodsByUserCodeAndDateRange(userCode: String, startDate: String, endDate: String): Flow<List<MoodEntity>>
    
    @Query("SELECT * FROM moods WHERE isSynced = 0")
    suspend fun getUnsyncedMoods(): List<MoodEntity>
    
    @Query("UPDATE moods SET remoteId = :remoteId, isSynced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long, remoteId: String)
} 