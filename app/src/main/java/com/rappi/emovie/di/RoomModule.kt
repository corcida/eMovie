package com.rappi.emovie.di

import android.content.Context
import com.rappi.emovie.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Provides
    fun provideTopRatedMovieLocalDataSource(db: AppDatabase) = db.topRatedMovieDao()

    @Provides
    fun provideUpcomingMovieLocalDataSource(db: AppDatabase) = db.upcomingMovieDao()

    @Provides
    fun provideGenreLocalDataSource(db: AppDatabase) = db.genreDao()

}