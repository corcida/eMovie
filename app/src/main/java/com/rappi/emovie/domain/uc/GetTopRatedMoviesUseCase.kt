package com.rappi.emovie.domain.uc

import com.rappi.emovie.data.database.entities.toDatabase
import com.rappi.emovie.data.repository.TopRatedMovieRepository
import com.rappi.emovie.domain.model.Movie
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val repository: TopRatedMovieRepository
) {
    suspend operator fun invoke():List<Movie>{
        val movies = repository.getTopRatedMovies()
        return if(movies.isNotEmpty()){
            repository.clearMovies()
            repository.insertMovies(movies.map { it.toDatabase() })
            movies
        }else{
            repository.getTopRatedMoviesFromDatabase()
        }
    }

}