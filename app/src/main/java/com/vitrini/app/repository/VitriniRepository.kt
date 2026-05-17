package com.vitrini.app.repository
import com.vitrini.app.data.local.AppDatabase
import com.vitrini.app.data.local.entity.BusinessEntity
import com.vitrini.app.data.local.entity.ProductStorageEntity
import com.vitrini.app.data.local.entity.ProductInteractionEntity
import com.vitrini.app.data.local.entity.SavedProductEntity
import com.vitrini.app.data.local.entity.UserEntity
import com.vitrini.app.utils.SessionManager
import com.vitrini.app.data.remote.RemoteDataSource
import java.util.UUID
import kotlinx.coroutines.flow.firstOrNull

class VitriniRepository(
    private val db: AppDatabase? = null,
    private val sessionManager: SessionManager? = null,
    private val remoteDataSource: RemoteDataSource? = null
) {

    suspend fun getActiveUser(): UserEntity? {
        val userId = sessionManager?.getActiveUserId() ?: return null
        return db?.userDao()?.getById(userId)
    }

    suspend fun saveLocalUser(user: UserEntity) {
        db?.userDao()?.insertOrUpdate(user)
    }

    suspend fun getProductById(productId: String): ProductStorageEntity? =
        db?.productStorageDao()?.getById(productId)

    suspend fun getBusinessStorage(): List<BusinessEntity> =
        db?.businessDao()?.getAll()?.firstOrNull() ?: emptyList()

    suspend fun getProductMedia(productId: String) =
        db?.productMediaDao()?.getByProductId(productId) ?: emptyList()

    suspend fun registerView(productId: String, lastingMs: Long?) = registerInteraction(productId, "VIEW", lastingMs = lastingMs)
    suspend fun registerRightSwipe(productId: String) = registerInteraction(productId, "LIKE")
    suspend fun registerLeftSwipe(productId: String) = registerInteraction(productId, "DISLIKE")
    suspend fun registerSkip(productId: String) = registerInteraction(productId, "SKIP")

    suspend fun saveProduct(productId: String) {
        val uid = sessionManager?.getActiveUserId() ?: return
        db?.savedProductDao()?.insert(SavedProductEntity(uid, productId, System.currentTimeMillis(), false))
        registerInteraction(productId, "SAVE")
    }

    suspend fun deleteSaved(productId: String) {
        val uid = sessionManager?.getActiveUserId() ?: return
        db?.savedProductDao()?.deleteByUserAndProduct(uid, productId)
        registerInteraction(productId, "UNSAVE")
    }

    suspend fun getSavedProducts(): List<String> {
        val uid = sessionManager?.getActiveUserId() ?: return emptyList()
        return db?.savedProductDao()?.getProductIdsByUser(uid) ?: emptyList()
    }

    suspend fun getRecommendedFeed(): List<ProductStorageEntity> {
        val uid = sessionManager?.getActiveUserId() ?: return emptyList()
        val products = db?.productStorageDao()?.getByStatus("ACTIVE").orEmpty()
        val likes = db?.productInteractionDao()?.countByUserAndType(uid, "LIKE") ?: 0
        val dislikes = db?.productInteractionDao()?.countByUserAndType(uid, "DISLIKE") ?: 0
        val ratio = (likes + 1).toDouble() / (dislikes + 1)
        return products.sortedByDescending { (it.price / 100.0) + ratio }
    }

    suspend fun getPendingInteractionsSync() = db?.productInteractionDao()?.getPendingSync() ?: emptyList()

    suspend fun getProducts() = db?.productStorageDao()?.getByStatus("ACTIVE") ?: emptyList()

    suspend fun getBusinesses() = db?.businessDao()?.getAll()?.firstOrNull() ?: emptyList()
    private suspend fun registerInteraction(productId: String, type: String, value: String? = null, lastingMs: Long? = null) {
        val uid = sessionManager?.getActiveUserId() ?: return
        db?.productInteractionDao()?.insert(
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