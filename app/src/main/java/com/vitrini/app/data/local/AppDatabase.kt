package com.vitrini.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vitrini.app.data.local.dao.*
import com.vitrini.app.data.local.entity.*

@Database
    (entities = [
    UserEntity::class,
    BusinessEntity::class,
    ProductEntity::class,
    LikeEntity::class,
    UserEntity::class,
    CategoryEntity::class,
    UserInterestEntity::class,
    BusinessEntity::class,
    ProductStorageEntity::class,
    ProductMediaEntity::class,
    FeedItemEntity::class,
    ProductInteractionEntity::class,
    SavedProductEntity::class,
    RecentSearchEntity::class,
    CartItemEntity::class
                ],
    version = 2,
    exportSchema = false
            )
abstract class AppDatabase: RoomDatabase() {
    abstract fun usuarioDao(): UserDao
    abstract fun emprendimientoDao(): BusinessDao
    abstract fun productoDao(): ProductDao
    abstract fun likeDao(): LikeDao

    abstract fun userStorageDao(): UserStorageDao
    abstract fun categoryDao(): CategoryDao
    abstract fun userInterestDao(): UserInterestDao
    abstract fun businessStorageDao(): BusinessStorageDao
    abstract fun productStorageDao(): ProductStorageDao
    abstract fun productMediaDao(): ProductMediaDao
    abstract fun feedItemDao(): FeedItemDao
    abstract fun productInteractionDao(): ProductInteractionDao
    abstract fun savedProductDao(): SavedProductDao
    abstract fun recentSearchDao(): RecentSearchDao
    abstract fun cartItemDao(): CartItemDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "vitrini_database").fallbackToDestructiveMigration().build().also { INSTANCE = it }


        }
    }
}