package com.vitrini.app.repository
import com.vitrini.app.data.local.AppDatabase
import com.vitrini.app.data.local.entity.ProductInteractionEntity
import com.vitrini.app.data.local.entity.SavedProductEntity
import com.vitrini.app.data.mapper.toLegacyModel
import com.vitrini.app.data.mapper.toModel
import com.vitrini.app.data.mapper.toEntity
import com.vitrini.app.model.Business
import com.vitrini.app.model.Product
import com.vitrini.app.model.User
import com.vitrini.app.utils.SessionManager
import java.util.UUID

class VitriniRepository (private val db: AppDatabase? = null, private val sessionManager: SessionManager? = null){

    suspend fun getActiveUser(): User? {
        val userId = sessionManager?.getActiveUserId() ?: return null
        return db?.userStorageDao()?.getUsersById(userId)?.toModel
    }
    suspend fun saveLocalUser(user: User) = db?.userStorageDao()?.save(user.toEntity())
    suspend fun getProductById(productId: String): Product? = db?.productStorageDao()?.getProductById(productId)?.toLegacyModel()
    suspend fun getBusinessStorage() = db?.businessStorageDao()?.getBusinesses() ?: emptyList()
    suspend fun getProductMedia(productId: String) = db?.productMediaDao()?.getMediaByProductId(productId) ?: emptyList()

    suspend fun registerView(productId: String, lastingMs: Long?) = registerInteraction(productId, "VIEW", lastingMs = lastingMs)
    suspend fun registerRightSwipe(productId: String) = registerInteraction(productId, "LIKE")
    suspend fun registerLeftSwipe(productId: String) = registerInteraction(productId, "DISLIKE")
    suspend fun registerSkip(productId: String) = registerInteraction(productId, "SKIP")

    suspend fun saveProduct(productId: String) {
        val uid = sessionManager?.getActiveUserId() ?: return
        db?.savedProductDao()?.saveProduct(SavedProductEntity(uid, productId, System.currentTimeMillis(), false))
        registerInteraction(productId, "SAVE")
    }

    suspend fun deleteSaved(productId: String) {
        val uid = sessionManager?.getActiveUserId() ?: return
        db?.savedProductDao()?.deleteSaved(uid, productId)
        registerInteraction(productId, "UNSAVE")
    }

    suspend fun getSavedProducts(): List<String> {
        val uid = sessionManager?.getActiveUserId() ?: return emptyList()
        return db?.savedProductDao()?.getSavedByUser(uid)?.map { it.productId } ?: emptyList()
    }

    suspend fun getRecommendedFeed(): List<Product> {
        val uid = sessionManager?.getActiveUserId() ?: return emptyList()
        val products = db?.productStorageDao()?.getActiveProducts().orEmpty()
        val likes = db?.productInteractionDao()?.countInteractionsByType(uid, "LIKE") ?: 0
        val dislikes = db?.productInteractionDao()?.countInteractionsByType(uid, "DISLIKE") ?: 0
        val ratio = (likes + 1).toDouble() / (dislikes + 1)
        return products.sortedByDescending { (it.price / 100.0) + ratio }.map { it.toLegacyModel() }
    }

    suspend fun getPendingInteractionsSync() = db?.productInteractionDao()?.getPendingInteractionsSync() ?: emptyList()

    private suspend fun registerInteraction(productId: String, type: String, value: String? = null, lastingMs: Long? = null) {
        val uid = sessionManager?.getActiveUserId() ?: return
        db?.productInteractionDao()?.saveInteraction(ProductInteractionEntity(UUID.randomUUID().toString(), uid, productId, type, value, lastingMs, System.currentTimeMillis(), false))
    }


}