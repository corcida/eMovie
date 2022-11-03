package com.rappi.emovie.data.repository

import com.rappi.emovie.data.database.dao.UpcomingMovieDao
import com.rappi.emovie.data.database.entities.UpcomingMovie
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
        val response: List<UpcomingMovie> = localDataSource.getUpcomingMovies()
        return response.map { it.toDomain() }
    }

    suspend fun insertMovies(movies:List<UpcomingMovie>){
        localDataSource.insertAll(movies)
    }

    suspend fun clearMovies(){
        localDataSource.deleteAll()
    }

}