package com.rappi.emovie.di

import com.rappi.emovie.data.database.dao.TopRatedMovieDao
import com.rappi.emovie.data.model.TopRatedMovieRemoteDataSource
import com.rappi.emovie.data.repository.TopRatedMovieRepository
import com.rappi.emovie.domain.uc.GetTopRatedMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideTopRatedMovieRepository(remoteDataSource: TopRatedMovieRemoteDataSource,
    localDataSource: TopRatedMovieDao
    ): TopRatedMovieRepository = TopRatedMovieRepository(remoteDataSource, localDataSource)

    @Provides
    fun provideGetTopRatedMovieUseCase(repository: TopRatedMovieRepository
    ): GetTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(repository)

}