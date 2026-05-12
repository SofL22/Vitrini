package com.vitrini.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vitrini.app.data.local.entity.LikeEntity

@Dao
interface LikeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarLike(like: LikeEntity)

    @Query("DELETE FROM likes WHERE userId = :usuarioId AND productId = :productoId")
    suspend fun eliminarLike(usuarioId: Int, productoId: Int)

    @Query("SELECT * FROM likes WHERE userId = :usuarioId")
    suspend fun obtenerLikesPorUsuario(usuarioId: Int): List<LikeEntity>

    @Query("SELECT COUNT(*) FROM likes WHERE userId = :usuarioId AND productId = :productoId")
    suspend fun productoTieneLike(usuarioId: Int, productoId: Int): Int

    @Query("DELETE FROM likes WHERE userId = :usuarioId")
    suspend fun eliminarLikesPorUsuario(usuarioId: Int)
}