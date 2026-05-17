package com.vitrini.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vitrini.app.data.local.entity.SavedProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedProduct: SavedProductEntity)

    @Update
    suspend fun update(savedProduct: SavedProductEntity)

    @Delete
    suspend fun delete(savedProduct: SavedProductEntity)

    @Query("SELECT * FROM saved_products WHERE userId = :userId AND productId = :productId LIMIT 1")
    suspend fun getById(userId: String, productId: String): SavedProductEntity?

    @Query("SELECT * FROM saved_products")
    fun getAll(): Flow<List<SavedProductEntity>>

    @Query("DELETE FROM saved_products WHERE userId = :userId AND productId = :productId")
    suspend fun deleteByUserAndProduct(userId: String, productId: String)

    @Query("SELECT productId FROM saved_products WHERE userId = :userId")
    suspend fun getProductIdsByUser(userId: String): List<String>
}