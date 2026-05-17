package com.vitrini.app.data.remote

import com.vitrini.app.data.dto.request.BusinessRegisterRequestDto
import com.vitrini.app.data.dto.request.CustomerRegisterRequestDto
import com.vitrini.app.data.dto.request.LoginRequestDto
import com.vitrini.app.data.dto.response.AuthResponseDto
import com.vitrini.app.data.dto.response.BusinessResponseDto
import com.vitrini.app.data.dto.response.UserResponseDto
import java.util.UUID

class FakeAuthApi : AuthApi {
    override suspend fun login(request: LoginRequestDto): AuthResponseDto {
        if (request.email.isBlank() || request.password.isBlank()) {
            throw IllegalArgumentException("Email and password are required")
        }

        val isBusiness = request.email.contains("business", ignoreCase = true)
        val userId = UUID.randomUUID().toString()

        return if (isBusiness) {
            AuthResponseDto(
                authToken = "fake-token-$userId",
                userId = userId,
                accountType = "BUSINESS",
                business = BusinessResponseDto(
                    id = userId,
                    name = "Demo Business",
                    description = "Business account",
                    category = "General",
                    location = "Unknown",
                    imageUrl = ""
                )
            )
        } else {
            AuthResponseDto(
                authToken = "fake-token-$userId",
                userId = userId,
                accountType = "CUSTOMER",
                user = UserResponseDto(
                    id = userId,
                    name = "Demo User",
                    email = request.email,
                    profilePicUrl = null,
                    userType = "CUSTOMER",
                    location = null,
                    status = "ACTIVE"
                )
            )
        }
    }

    override suspend fun registerCustomer(request: CustomerRegisterRequestDto): AuthResponseDto {
        val userId = UUID.randomUUID().toString()
        return AuthResponseDto(
            authToken = "fake-token-$userId",
            userId = userId,
            accountType = "CUSTOMER",
            user = UserResponseDto(
                id = userId,
                name = request.fullName,
                email = request.email,
                profilePicUrl = null,
                userType = "CUSTOMER",
                location = null,
                status = "ACTIVE"
            )
        )
    }

    override suspend fun registerBusiness(request: BusinessRegisterRequestDto): AuthResponseDto {
        val userId = UUID.randomUUID().toString()
        return AuthResponseDto(
            authToken = "fake-token-$userId",
            userId = userId,
            accountType = "BUSINESS",
            business = BusinessResponseDto(
                id = userId,
                name = request.businessName,
                description = "Owned by ${request.ownerName}",
                category = request.businessCategory.orEmpty(),
                location = request.phoneNumber.orEmpty(),
                imageUrl = ""
            )
        )
    }
}