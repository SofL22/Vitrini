package com.vitrini.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vitrini.app.data.local.entity.ProductStorageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductStorageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductStorageEntity)

    @Update
    suspend fun update(product: ProductStorageEntity)

    @Delete
    suspend fun delete(product: ProductStorageEntity)

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ProductStorageEntity?

    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<ProductStorageEntity>>

    @Query("SELECT * FROM products WHERE status = :status")
    suspend fun getByStatus(status: String): List<ProductStorageEntity>
}