package com.example.zenite.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zenite.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    @Query("SELECT * FROM users WHERE code = :code")
    suspend fun getUserByCode(code: String): UserEntity?
    
    @Query("SELECT * FROM users")
    fun observeAllUsers(): Flow<List<UserEntity>>
    
    @Query("DELETE FROM users WHERE code = :code")
    suspend fun deleteUser(code: String)
} 