package com.vitrini.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vitrini.app.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarUsuario(usuario: UserEntity): Long

    @Query("SELECT * FROM users WHERE mail = :correo AND password = :password LIMIT 1")
    suspend fun login(correo: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun obtenerUsuarioPorId(id: Int): UserEntity?

    @Query("SELECT COUNT(*) FROM users WHERE mail = :correo")
    suspend fun existeCorreo(correo: String): Int

    @Query("SELECT * FROM users")
    suspend fun obtenerUsuarios(): List<UserEntity>
}