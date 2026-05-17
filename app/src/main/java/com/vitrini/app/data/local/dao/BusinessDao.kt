package com.vitrini.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vitrini.app.data.local.entity.BusinessEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(business: BusinessEntity)

    @Update
    suspend fun update(business: BusinessEntity)

    @Delete
    suspend fun delete(business: BusinessEntity)

    @Query("SELECT * FROM businesses WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): BusinessEntity?

    @Query("SELECT * FROM businesses")
    fun getAll(): Flow<List<BusinessEntity>>
}