package com.vitrini.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vitrini.app.data.local.entity.ProductInteractionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductInteractionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(interaction: ProductInteractionEntity)

    @Update
    suspend fun update(interaction: ProductInteractionEntity)

    @Delete
    suspend fun delete(interaction: ProductInteractionEntity)

    @Query("SELECT * FROM product_interactions WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ProductInteractionEntity?

    @Query("SELECT * FROM product_interactions")
    fun getAll(): Flow<List<ProductInteractionEntity>>

    @Query("SELECT * FROM product_interactions WHERE synced = 0")
    suspend fun getPendingSync(): List<ProductInteractionEntity>

    @Query("SELECT COUNT(*) FROM product_interactions WHERE userId = :userId AND type = :type")
    suspend fun countByUserAndType(userId: String, type: String): Int
}