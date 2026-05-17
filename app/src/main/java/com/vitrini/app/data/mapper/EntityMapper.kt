package com.vitrini.app.data.mapper
import com.vitrini.app.data.local.entity.BusinessEntity
import com.vitrini.app.data.local.entity.ProductStorageEntity
import com.vitrini.app.data.local.entity.UserEntity
import com.vitrini.app.data.dto.response.BusinessResponseDto
import com.vitrini.app.data.dto.response.ProductResponseDto
import com.vitrini.app.data.dto.response.UserResponseDto

object EntityMapper {
    fun toUserResponseDto(entity: UserEntity): UserResponseDto =
        UserResponseDto(
            id = entity.id,
            name = entity.name,
            email = entity.mail,
            profilePicUrl = entity.profilePicUrl,
            userType = entity.userType,
            location = entity.location,
            status = entity.status
        )

    fun toProductResponseDto(entity: ProductStorageEntity): ProductResponseDto =
        ProductResponseDto(
            id = entity.id,
            businessId = entity.businessId,
            name = entity.name,
            description = entity.description,
            price = entity.price,
            currency = entity.currency,
            categoryId = entity.categoryId,
            stock = entity.stock,
            status = entity.status
        )

    fun toBusinessResponseDto(entity: BusinessEntity): BusinessResponseDto =
        BusinessResponseDto(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            category = entity.category,
            location = entity.location,
            imageUrl = entity.imageUrl
        )
}