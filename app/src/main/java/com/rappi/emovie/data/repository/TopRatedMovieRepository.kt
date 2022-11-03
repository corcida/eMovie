package com.rappi.emovie.data.repository

import com.rappi.emovie.data.database.dao.TopRatedMovieDao
import com.rappi.emovie.data.database.entities.TopRatedMovieEntity
import com.rappi.emovie.data.network.TopRatedMovieRemoteDataSource
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.model.toDomain
import javax.inject.Inject

class TopRatedMovieRepository @Inject constructor(
    private val remoteDataSource: TopRatedMovieRemoteDataSource,
    private val localDataSource: TopRatedMovieDao
) {

    suspend fun getTopRatedMovies() : List<Movie> {
        val data = remoteDataSource.getTopRatedMovies()
        return data.data?.results?.map { it.toDomain() } ?: emptyList()
    }

    suspend fun getTopRatedMoviesFromDatabase():List<Movie>{
        val response: List<TopRatedMovieEntity> = localDataSource.getTopRatedMovies()
        return response.map { it.toDomain() }
    }

    suspend fun insertMovies(movies:List<TopRatedMovieEntity>){
        localDataSource.insertAll(movies)
    }

    suspend fun clearMovies(){
        localDataSource.deleteAll()
    }

}