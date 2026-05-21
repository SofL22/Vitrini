package com.vitrini.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vitrini.app.data.local.dao.BusinessDao
import com.vitrini.app.data.local.dao.ProductInteractionDao
import com.vitrini.app.data.local.dao.ProductMediaDao
import com.vitrini.app.data.local.dao.ProductStorageDao
import com.vitrini.app.data.local.dao.SavedProductDao
import com.vitrini.app.data.local.dao.UserDao
import com.vitrini.app.data.local.entity.BusinessEntity
import com.vitrini.app.data.local.entity.ProductInteractionEntity
import com.vitrini.app.data.local.entity.ProductMediaEntity
import com.vitrini.app.data.local.entity.ProductStorageEntity
import com.vitrini.app.data.local.entity.SavedProductEntity
import com.vitrini.app.data.local.entity.UserEntity


@Database(
    entities = [
        UserEntity::class,
        BusinessEntity::class,
        ProductStorageEntity::class,
        ProductMediaEntity::class,
        ProductInteractionEntity::class,
        SavedProductEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun businessDao(): BusinessDao
    abstract fun productStorageDao(): ProductStorageDao
    abstract fun productMediaDao(): ProductMediaDao
    abstract fun productInteractionDao(): ProductInteractionDao
    abstract fun savedProductDao(): SavedProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vitrini_database"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
    }
}