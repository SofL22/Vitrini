package com.vitrini.app.data.remote

import com.vitrini.app.data.dto.request.BusinessRegisterRequestDto
import com.vitrini.app.data.dto.request.CustomerRegisterRequestDto
import com.vitrini.app.data.dto.request.LoginRequestDto
import com.vitrini.app.data.dto.response.AuthResponseDto
interface AuthApi {
    suspend fun login(request: LoginRequestDto): AuthResponseDto
    suspend fun registerCustomer(request: CustomerRegisterRequestDto): AuthResponseDto
    suspend fun registerBusiness(request: BusinessRegisterRequestDto): AuthResponseDto
}