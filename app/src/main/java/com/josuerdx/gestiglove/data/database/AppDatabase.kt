package com.josuerdx.gestiglove.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.josuerdx.gestiglove.data.dao.PendingUserDao
import com.josuerdx.gestiglove.data.dao.UserCredentialsDao
import com.josuerdx.gestiglove.data.model.PendingUser
import com.josuerdx.gestiglove.data.model.UserCredentials

/**
 * Base de datos para manejar datos locales.
 */
@Database(entities = [UserCredentials::class, PendingUser::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userCredentialsDao(): UserCredentialsDao
    abstract fun pendingUserDao(): PendingUserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gestiglove_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}