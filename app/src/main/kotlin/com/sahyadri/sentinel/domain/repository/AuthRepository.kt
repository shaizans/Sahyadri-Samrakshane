package com.sahyadri.sentinel.domain.repository

import com.sahyadri.sentinel.core.util.Resource
import com.sahyadri.sentinel.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Resource<User>>
    fun register(email: String, password: String): Flow<Resource<User>>
    fun logout()
    fun getCurrentUser(): User?
    fun isUserLoggedIn(): Boolean
}
