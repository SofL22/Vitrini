package com.vitrini.app.data.local.dao

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.vitrini.app.data.local.entity.BusinessEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(business: BusinessEntity)

    @Query("SELECT * FROM businesses WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): BusinessEntity?

    @Query("SELECT * FROM businesses WHERE ownerUserId = :ownerUserId LIMIT 1")
    suspend fun getByOwnerUserId(ownerUserId: String): BusinessEntity?

    @Query("DELETE FROM businesses")
    suspend fun clear()

    @Query("SELECT * FROM businesses")
    fun getAll(): Flow<List<BusinessEntity>>
}