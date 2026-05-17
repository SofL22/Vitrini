package com.vitrini.app.data.local

import android.content.Context
import com.vitrini.app.data.local.entity.BusinessEntity
import com.vitrini.app.data.local.entity.ProductInteractionEntity
import com.vitrini.app.data.local.entity.ProductMediaEntity
import com.vitrini.app.data.local.entity.ProductStorageEntity
import com.vitrini.app.data.local.entity.SavedProductEntity
import com.vitrini.app.data.local.entity.UserEntity

class AppDatabase private constructor() {
    val users: MutableList<UserEntity> = mutableListOf()
    val businesses: MutableList<BusinessEntity> = mutableListOf()
    val products: MutableList<ProductStorageEntity> = mutableListOf()
    val productMedia: MutableList<ProductMediaEntity> = mutableListOf()
    val productInteractions: MutableList<ProductInteractionEntity> = mutableListOf()
    val savedProducts: MutableList<SavedProductEntity> = mutableListOf()

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getDatabase(@Suppress("UNUSED_PARAMETER") context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppDatabase().also { INSTANCE = it }
            }
    }
}