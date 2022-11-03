package com.rappi.emovie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rappi.emovie.data.database.dao.TopRatedMovieDao
import com.rappi.emovie.data.database.dao.UpcomingMovieDao
import com.rappi.emovie.data.database.entities.TopRatedMovie
import com.rappi.emovie.data.database.entities.UpcomingMovie


@Database(entities = [TopRatedMovie::class, UpcomingMovie::class],
    version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun topRatedMovieDao(): TopRatedMovieDao

    abstract fun upcomingMovieDao(): UpcomingMovieDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "emovie")
                .fallbackToDestructiveMigration()
                .build()
    }
}