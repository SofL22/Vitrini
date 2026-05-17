package com.vitrini.app.data.remote
import com.vitrini.app.data.dto.request.BusinessRegisterRequestDto
import com.vitrini.app.data.dto.request.CustomerRegisterRequestDto
import com.vitrini.app.data.dto.request.LoginRequestDto
import com.vitrini.app.data.dto.response.AuthResponseDto
class RemoteAuthDataSource(private val authApi: AuthApi) {
    suspend fun login(request: LoginRequestDto): AuthResponseDto = authApi.login(request)
    suspend fun registerCustomer(request: CustomerRegisterRequestDto): AuthResponseDto = authApi.registerCustomer(request)
    suspend fun registerBusiness(request: BusinessRegisterRequestDto): AuthResponseDto = authApi.registerBusiness(request)
}