package com.vitrini.app.repository
import com.vitrini.app.data.local.AppDatabase
import com.vitrini.app.data.local.entity.BusinessEntity
import com.vitrini.app.data.local.entity.ProductStorageEntity
import com.vitrini.app.data.local.entity.ProductInteractionEntity
import com.vitrini.app.data.local.entity.SavedProductEntity
import com.vitrini.app.data.local.entity.UserEntity
import com.vitrini.app.utils.SessionManager
import java.util.UUID

class VitriniRepository(
    private val db: AppDatabase? = null,
    private val sessionManager: SessionManager? = null
) {

    suspend fun getActiveUser(): UserEntity? {
        val userId = sessionManager?.getActiveUserId() ?: return null
        return db?.users?.firstOrNull { it.id == userId }
    }

    suspend fun saveLocalUser(user: UserEntity) {
        db?.users?.removeAll { it.id == user.id }
        db?.users?.add(user)
    }

    suspend fun getProductById(productId: String): ProductStorageEntity? =
        db?.products?.firstOrNull { it.id == productId }

    suspend fun getBusinessStorage(): List<BusinessEntity> =
        db?.businesses ?: emptyList()

    suspend fun getProductMedia(productId: String) =
        db?.productMedia?.filter { it.productId == productId } ?: emptyList()

    suspend fun registerView(productId: String, lastingMs: Long?) = registerInteraction(productId, "VIEW", lastingMs = lastingMs)
    suspend fun registerRightSwipe(productId: String) = registerInteraction(productId, "LIKE")
    suspend fun registerLeftSwipe(productId: String) = registerInteraction(productId, "DISLIKE")
    suspend fun registerSkip(productId: String) = registerInteraction(productId, "SKIP")

    suspend fun saveProduct(productId: String) {
        val uid = sessionManager?.getActiveUserId() ?: return
        db?.savedProducts?.add(SavedProductEntity(uid, productId, System.currentTimeMillis(), false))
        registerInteraction(productId, "SAVE")
    }

    suspend fun deleteSaved(productId: String) {
        val uid = sessionManager?.getActiveUserId() ?: return
        db?.savedProducts?.removeAll { it.userId == uid && it.productId == productId }
        registerInteraction(productId, "UNSAVE")
    }

    suspend fun getSavedProducts(): List<String> {
        val uid = sessionManager?.getActiveUserId() ?: return emptyList()
        return db?.savedProducts?.filter { it.userId == uid }?.map { it.productId } ?: emptyList()
    }

    suspend fun getRecommendedFeed(): List<ProductStorageEntity> {
        val uid = sessionManager?.getActiveUserId() ?: return emptyList()
        val products = db?.products?.filter { it.status == "ACTIVE" }.orEmpty()
        val likes = db?.productInteractions?.count { it.userId == uid && it.type == "LIKE" } ?: 0
        val dislikes = db?.productInteractions?.count { it.userId == uid && it.type == "DISLIKE" } ?: 0
        val ratio = (likes + 1).toDouble() / (dislikes + 1)
        return products.sortedByDescending { (it.price / 100.0) + ratio }
    }

    suspend fun getPendingInteractionsSync() = db?.productInteractions?.filter { !it.synced } ?: emptyList()

    suspend fun getProducts() = db?.products?.filter { it.status == "ACTIVE" } ?: emptyList()

    suspend fun getBusinesses() = db?.businesses ?: emptyList()
    private suspend fun registerInteraction(productId: String, type: String, value: String? = null, lastingMs: Long? = null) {
        val uid = sessionManager?.getActiveUserId() ?: return
        db?.productInteractions?.add(
            ProductInteractionEntity(
                UUID.randomUUID().toString(),
                uid,
                productId,
                type,
                value,
                lastingMs,
                System.currentTimeMillis(),
                false
            )
        )
    }


}