package com.rappi.emovie.di

import com.rappi.emovie.data.database.dao.GenreDao
import com.rappi.emovie.data.database.dao.TopRatedMovieDao
import com.rappi.emovie.data.database.dao.UpcomingMovieDao
import com.rappi.emovie.data.network.GenreRemoteDataSource
import com.rappi.emovie.data.network.TopRatedMovieRemoteDataSource
import com.rappi.emovie.data.network.UpcomingMovieRemoteDataSource
import com.rappi.emovie.data.network.VideoRemoteDataSource
import com.rappi.emovie.data.repository.GenreRepository
import com.rappi.emovie.data.repository.TopRatedMovieRepository
import com.rappi.emovie.data.repository.UpcomingMovieRepository
import com.rappi.emovie.data.repository.VideoRepository
import com.rappi.emovie.domain.uc.*
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
    fun provideUpcomingMovieRepository(remoteDataSource: UpcomingMovieRemoteDataSource,
                                       localDataSource: UpcomingMovieDao
    ): UpcomingMovieRepository = UpcomingMovieRepository(remoteDataSource, localDataSource)

    @Provides
    fun provideGenreRepository(remoteDataSource: GenreRemoteDataSource,
                                       localDataSource: GenreDao):
            GenreRepository = GenreRepository(remoteDataSource, localDataSource)

    @Provides
    fun provideVideoRepository(remoteDataSource: VideoRemoteDataSource):
            VideoRepository = VideoRepository(remoteDataSource)

    @Provides
    fun provideGetTopRatedMovieUseCase(repository: TopRatedMovieRepository
    ): GetTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(repository)

    @Provides
    fun provideGetUpcomingMovieUseCase(repository: UpcomingMovieRepository
    ): GetUpcomingMoviesUseCase = GetUpcomingMoviesUseCase(repository)

    @Provides
    fun provideGetEnglishMovieUseCase(repository: TopRatedMovieRepository
    ): GetEnglishMoviesUseCase = GetEnglishMoviesUseCase(repository)

    @Provides
    fun provideGetDateFilteredMovieUseCase(repository: TopRatedMovieRepository
    ): GetDateFilteredMoviesUseCase = GetDateFilteredMoviesUseCase(repository)

    @Provides
    fun provideGenreUseCase(repository: GenreRepository
    ): GetGenresUseCase = GetGenresUseCase(repository)

    @Provides
    fun provideVideoUseCase(repository: VideoRepository
    ): GetVideosUseCase = GetVideosUseCase(repository)

}