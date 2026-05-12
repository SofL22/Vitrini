package com.vitrini.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vitrini.app.data.local.entity.BusinessEntity

@Dao
interface BusinessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEmprendimientos(emprendimientos: List<BusinessEntity>)

    @Query("SELECT * FROM businesses")
    suspend fun obtenerEmprendimientos(): List<BusinessEntity>

    @Query("SELECT * FROM businesses WHERE id = :id LIMIT 1")
    suspend fun obtenerEmprendimientoPorId(id: Int): BusinessEntity?

    @Query("SELECT COUNT(*) FROM businesses")
    suspend fun contarEmprendimientos(): Int
}