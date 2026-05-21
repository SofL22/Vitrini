package com.vitrini.app.repository

import com.vitrini.app.data.dto.request.BusinessRegisterRequestDto
import com.vitrini.app.data.dto.request.CustomerRegisterRequestDto
import com.vitrini.app.data.dto.request.LoginRequestDto
import com.vitrini.app.data.dto.response.AuthResponseDto
import com.vitrini.app.data.dto.response.BusinessResponseDto
import com.vitrini.app.data.dto.response.UserResponseDto
import com.vitrini.app.data.local.dao.BusinessDao
import com.vitrini.app.data.local.dao.UserDao
import com.vitrini.app.data.local.entity.BusinessEntity
import com.vitrini.app.data.local.entity.UserEntity
import com.vitrini.app.data.remote.RemoteAuthDataSource
import com.vitrini.app.data.local.preferences.SessionData
import com.vitrini.app.utils.SessionManager
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class AuthRepository(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val sessionManager: SessionManager,
    private val userDao: UserDao,
    private val businessDao: BusinessDao
) {
    suspend fun login(request: LoginRequestDto): AuthResponseDto {
        val user = userDao.getByEmail(request.email.trim())
            ?: throw IllegalArgumentException("User not found. Please register first")

        if (user.password != request.password) {
            throw IllegalArgumentException("Incorrect password")
        }

        val business = if (user.userType == "BUSINESS") businessDao.getById(user.id) else null

        val response = AuthResponseDto(
            authToken = "local-token-${user.id}",
            userId = user.id,
            accountType = user.userType,
            user = if (user.userType == "CUSTOMER") {
                UserResponseDto(
                    id = user.id,
                    name = user.name,
                    email = user.mail,
                    profilePicUrl = user.profilePicUrl,
                    userType = user.userType,
                    location = user.location,
                    status = user.status
                )
            } else null,
            business = business?.let {
                BusinessResponseDto(
                    id = it.id,
                    name = it.name,
                    description = "Owned by ${it.ownerName}",
                    category = it.category,
                    location = it.location,
                    imageUrl = it.imageUrl
                )
            }
        )

        persistAuthResult(response)
        return response
    }

    suspend fun registerCustomer(request: CustomerRegisterRequestDto): AuthResponseDto {
        if (userDao.getByEmail(request.email.trim()) != null) {
            throw IllegalArgumentException("Email already registered")
        }

        val response = remoteAuthDataSource.registerCustomer(request)
        persistAuthResult(response, request.password)
        return response
    }

    suspend fun registerBusiness(request: BusinessRegisterRequestDto): AuthResponseDto {
        if (userDao.getByEmail(request.email.trim()) != null) {
            throw IllegalArgumentException("Email already registered")
        }

        val response = remoteAuthDataSource.registerBusiness(request)
        persistAuthResult(response, request.password, request.email)
        return response
    }

    fun logout() {
        sessionManager.clearSession()
    }

    fun observeSession(): Flow<SessionData> = sessionManager.observeSession()

    private suspend fun persistAuthResult(
        response: AuthResponseDto,
        rawPassword: String = "",
        businessEmail: String = ""
    ) {
        sessionManager.saveSession(response.authToken, response.userId, response.accountType)
        val now = System.currentTimeMillis()

        response.user?.let {
            userDao.insertOrUpdate(
                UserEntity(
                    id = it.id,
                    name = it.name,
                    mail = it.email,
                    password = rawPassword,
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
            val businessUserId = response.userId.ifBlank { UUID.randomUUID().toString() }
            userDao.insertOrUpdate(
                UserEntity(
                    id = businessUserId,
                    name = it.name,
                    mail = businessEmail,
                    password = rawPassword,
                    userType = "BUSINESS",
                    createdAt = now,
                    updatedAt = now
                )
            )

            businessDao.insertOrUpdate(
                BusinessEntity(
                    id = it.id,
                    ownerUserId = businessUserId,
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
