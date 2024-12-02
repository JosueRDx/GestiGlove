package com.josuerdx.gestiglove.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.josuerdx.gestiglove.data.dao.PendingUserDao
import com.josuerdx.gestiglove.data.dao.UserCredentialsDao
import com.josuerdx.gestiglove.data.model.PendingUser
import com.josuerdx.gestiglove.data.model.UserCredentials

/**
 * Base de datos para manejar datos locales.
 */
@Database(entities = [UserCredentials::class, PendingUser::class], version = 3, exportSchema = false)
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
                    .addMigrations(MIGRATION_2_3)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Añadir la columna 'plainPassword' con un valor predeterminado
                db.execSQL("ALTER TABLE user_credentials ADD COLUMN plainPassword TEXT NOT NULL DEFAULT 'default_password'")
            }
        }
    }
}