package com.vitrini.app.repository

import com.vitrini.app.data.dto.request.BusinessRegisterRequestDto
import com.vitrini.app.data.dto.request.CustomerRegisterRequestDto
import com.vitrini.app.data.dto.request.LoginRequestDto
import com.vitrini.app.data.dto.response.AuthResponseDto
import com.vitrini.app.data.local.dao.BusinessDao
import com.vitrini.app.data.local.dao.UserDao
import com.vitrini.app.data.local.entity.BusinessEntity
import com.vitrini.app.data.local.entity.UserEntity
import com.vitrini.app.data.remote.RemoteAuthDataSource
import com.vitrini.app.utils.SessionManager

class AuthRepository(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val sessionManager: SessionManager,
    private val userDao: UserDao,
    private val businessDao: BusinessDao
) {
    suspend fun login(request: LoginRequestDto): AuthResponseDto {
        val response = remoteAuthDataSource.login(request)
        persistAuthResult(response)
        return response
    }

    suspend fun registerCustomer(request: CustomerRegisterRequestDto): AuthResponseDto {
        val response = remoteAuthDataSource.registerCustomer(request)
        persistAuthResult(response)
        return response
    }

    suspend fun registerBusiness(request: BusinessRegisterRequestDto): AuthResponseDto {
        val response = remoteAuthDataSource.registerBusiness(request)
        persistAuthResult(response)
        return response
    }

    fun logout() {
        sessionManager.clearSession()
    }

    private suspend fun persistAuthResult(response: AuthResponseDto) {
        sessionManager.saveSession(response.authToken, response.userId, response.accountType)
        val now = System.currentTimeMillis()

        response.user?.let {
            userDao.insertOrUpdate(
                UserEntity(
                    id = it.id,
                    name = it.name,
                    mail = it.email,
                    profilePicUrl = it.profilePicUrl,
                    userType = it.userType,
                    location = it.location,
                    status = it.status,
                    createdAt = now,
                    updatedAt = now
                )
            )
        }

        response.business?.let {
            businessDao.insertOrUpdate(
                BusinessEntity(
                    id = it.id,
                    ownerName = it.name,
                    name = it.name,
                    category = it.category,
                    location = it.location,
                    imageUrl = it.imageUrl
                )
            )
        }
    }
}