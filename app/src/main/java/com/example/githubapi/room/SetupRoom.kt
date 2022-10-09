package com.example.githubapi.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.githubapi.model.Favorite
import com.example.githubapi.model.UserLogin

@Database(
    entities = [Favorite::class,UserLogin::class],
    version = 3,
    exportSchema = false
)
abstract class SetupRoom : RoomDatabase() {

    abstract fun daoFavorite() : DaoFavorite
    abstract fun daoUserLogin() : DaoUserLogin

    companion object {

        @Volatile private var instance : SetupRoom? = null
        private val LOCK = Any()

        val migration_1_2:Migration = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE userLogin ADD COLUMN name TEXT DEFAULT '' ")
            }

        }

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            SetupRoom::class.java,
            "Prediction"
        ).addMigrations(migration_1_2).fallbackToDestructiveMigration().build()

    }

}