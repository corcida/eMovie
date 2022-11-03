package com.rappi.emovie.domain.uc

import com.rappi.emovie.data.repository.TopRatedMovieRepository
import com.rappi.emovie.domain.model.Movie
import javax.inject.Inject

class GetDateFilteredMoviesUseCase @Inject constructor(
    private val repository: TopRatedMovieRepository
) {
    suspend operator fun invoke():List<Movie>{
        val movies = repository.getTopRatedMoviesFromDatabase()
        return movies.filter { it.release_date.contains("2020") }.take(6)
    }

}