package com.rappi.emovie.data.repository

import com.rappi.emovie.data.database.dao.UpcomingMovieDao
import com.rappi.emovie.data.database.entities.UpcomingMovieEntity
import com.rappi.emovie.data.network.UpcomingMovieRemoteDataSource
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.model.toDomain
import javax.inject.Inject

class UpcomingMovieRepository @Inject constructor(
    private val remoteDataSource: UpcomingMovieRemoteDataSource,
    private val localDataSource: UpcomingMovieDao
) {

    suspend fun getUpcomingMovies() : List<Movie> {
        val data = remoteDataSource.getUpcomingMovies()
        return data.data?.results?.map { it.toDomain() } ?: emptyList()
    }

    suspend fun getUpcomingMoviesFromDatabase():List<Movie>{
        val response: List<UpcomingMovieEntity> = localDataSource.getUpcomingMovies()
        return response.map { it.toDomain() }
    }

    suspend fun insertMovies(movies:List<UpcomingMovieEntity>){
        localDataSource.insertAll(movies)
    }

    suspend fun clearMovies(){
        localDataSource.deleteAll()
    }

}