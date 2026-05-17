package com.vitrini.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vitrini.app.data.local.entity.ProductMediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductMediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(media: ProductMediaEntity)

    @Update
    suspend fun update(media: ProductMediaEntity)

    @Delete
    suspend fun delete(media: ProductMediaEntity)

    @Query("SELECT * FROM product_media WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ProductMediaEntity?

    @Query("SELECT * FROM product_media")
    fun getAll(): Flow<List<ProductMediaEntity>>

    @Query("SELECT * FROM product_media WHERE productId = :productId ORDER BY `order` ASC")
    suspend fun getByProductId(productId: String): List<ProductMediaEntity>
}