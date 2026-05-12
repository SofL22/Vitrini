package com.vitrini.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vitrini.app.data.local.entity.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProductos(productos: List<ProductEntity>)

    @Query("SELECT * FROM products")
    suspend fun obtenerProductos(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun obtenerProductoPorId(id: Int): ProductEntity?

    @Query("SELECT * FROM products WHERE category IN (:categorias)")
    suspend fun obtenerProductosPorCategorias(categorias: List<String>): List<ProductEntity>

    @Query("SELECT COUNT(*) FROM products")
    suspend fun contarProductos(): Int
}