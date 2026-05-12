package com.vitrini.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vitrini.app.data.local.dao.BusinessDao
import com.vitrini.app.data.local.dao.LikeDao
import com.vitrini.app.data.local.dao.ProductDao
import com.vitrini.app.data.local.dao.UserDao
import com.vitrini.app.data.local.entity.BusinessEntity
import com.vitrini.app.data.local.entity.LikeEntity
import com.vitrini.app.data.local.entity.ProductEntity
import com.vitrini.app.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        BusinessEntity::class,
        ProductEntity::class,
        LikeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun usuarioDao(): UserDao
    abstract fun emprendimientoDao(): BusinessDao
    abstract fun productoDao(): ProductDao
    abstract fun likeDao(): LikeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vitrini_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}