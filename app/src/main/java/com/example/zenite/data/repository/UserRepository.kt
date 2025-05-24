package com.example.zenite.data.repository

import com.example.zenite.data.api.AuthApi
import com.example.zenite.data.api.model.LoginRequest
import com.example.zenite.data.api.model.RegisterRequest
import com.example.zenite.data.local.dao.UserDao
import com.example.zenite.data.local.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val authApi: AuthApi
) {
    val users: Flow<List<UserEntity>> = userDao.observeAllUsers()
    
    suspend fun login(code: String, password: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val localUser = userDao.getUserByCode(code)
            if (localUser != null && localUser.password == password) {
                return@withContext Result.success(code)
            }
            
            val response = authApi.login(LoginRequest(code, password))
            
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    userDao.insertUser(UserEntity(code, password))
                    return@withContext Result.success(loginResponse.code)
                }
            }
            
            return@withContext Result.failure(Exception(response.errorBody()?.string() ?: "Login failed"))
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }
    
    suspend fun register(password: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = authApi.register(RegisterRequest(password))
            
            if (response.isSuccessful) {
                response.body()?.let { registerResponse ->
                    userDao.insertUser(UserEntity(registerResponse.code, password))
                    return@withContext Result.success(registerResponse.code)
                }
            }
            
            return@withContext Result.failure(Exception(response.errorBody()?.string() ?: "Registration failed"))
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }
    
    suspend fun getUserByCode(code: String): UserEntity? = withContext(Dispatchers.IO) {
        return@withContext userDao.getUserByCode(code)
    }
    
    suspend fun deleteUser(code: String) = withContext(Dispatchers.IO) {
        userDao.deleteUser(code)
    }
} 