package com.rappi.emovie.di

import com.rappi.emovie.data.database.dao.TopRatedMovieDao
import com.rappi.emovie.data.database.dao.UpcomingMovieDao
import com.rappi.emovie.data.network.TopRatedMovieRemoteDataSource
import com.rappi.emovie.data.network.UpcomingMovieRemoteDataSource
import com.rappi.emovie.data.repository.TopRatedMovieRepository
import com.rappi.emovie.data.repository.UpcomingMovieRepository
import com.rappi.emovie.domain.uc.GetTopRatedMoviesUseCase
import com.rappi.emovie.domain.uc.GetUpcomingMoviesUseCase
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

    @Provides
    fun provideUpcomingMovieRepository(remoteDataSource: UpcomingMovieRemoteDataSource,
                                       localDataSource: UpcomingMovieDao
    ): UpcomingMovieRepository = UpcomingMovieRepository(remoteDataSource, localDataSource)

    @Provides
    fun provideGetUpcomingMovieUseCase(repository: UpcomingMovieRepository
    ): GetUpcomingMoviesUseCase = GetUpcomingMoviesUseCase(repository)

}