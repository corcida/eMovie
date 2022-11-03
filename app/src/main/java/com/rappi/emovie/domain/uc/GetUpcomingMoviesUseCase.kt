package com.rappi.emovie.domain.uc

import com.rappi.emovie.data.database.entities.toUpcomingDatabase
import com.rappi.emovie.data.repository.UpcomingMovieRepository
import com.rappi.emovie.domain.model.Movie
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val repository: UpcomingMovieRepository
) {
    suspend operator fun invoke():List<Movie>{
        val movies = repository.getUpcomingMovies()
        return if(movies.isNotEmpty()){
            repository.clearMovies()
            repository.insertMovies(movies.map { it.toUpcomingDatabase() })
            movies
        }else{
            repository.getUpcomingMoviesFromDatabase()
        }
    }

}