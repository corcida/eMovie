package com.rappi.emovie.data.repository

import com.rappi.emovie.data.database.dao.GenreDao
import com.rappi.emovie.data.database.dao.UpcomingMovieDao
import com.rappi.emovie.data.database.entities.GenreEntity
import com.rappi.emovie.data.database.entities.UpcomingMovieEntity
import com.rappi.emovie.data.network.GenreRemoteDataSource
import com.rappi.emovie.domain.model.Genre
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.model.toDomain
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val remoteDataSource: GenreRemoteDataSource,
    private val localDataSource: GenreDao
) {

    suspend fun getGenres() : List<Genre> {
        val data = remoteDataSource.getGenres()
        return data.data?.genres?.map { it.toDomain() } ?: emptyList()
    }

    suspend fun getGenresFromDatabase():List<Genre>{
        val response: List<GenreEntity> = localDataSource.getGenres()
        return response.map { it.toDomain() }
    }

    suspend fun insertGenres(genres:List<GenreEntity>){
        localDataSource.insertAll(genres)
    }

    suspend fun clearGenres(){
        localDataSource.deleteAll()
    }

}