package com.rappi.emovie.domain

import com.rappi.emovie.data.repository.TopRatedMovieRepository
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.uc.GetDateFilteredMoviesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetDateFilteredMoviesUseCaseTest {

    @RelaxedMockK
    private lateinit var topRatedMovieRepository: TopRatedMovieRepository

    lateinit var getDateFilteredMoviesUseCase: GetDateFilteredMoviesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getDateFilteredMoviesUseCase = GetDateFilteredMoviesUseCase(topRatedMovieRepository)
    }

    @Test
    fun `when the database has some movie data then return only 2020 movies from database`() = runBlocking {
        //Given
        val dateFilteredMovies = listOf(Movie(0, "test", "","",
            "2020-11-11"))
        val movies = listOf(Movie(1, "test"))
        movies.plus(dateFilteredMovies)
        coEvery { topRatedMovieRepository.getTopRatedMoviesFromDatabase() } returns movies

        //When
        val response = getDateFilteredMoviesUseCase()

        //Then
        assert(response == dateFilteredMovies)
    }

}