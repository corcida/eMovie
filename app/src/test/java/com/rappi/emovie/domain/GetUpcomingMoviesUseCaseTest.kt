package com.rappi.emovie.domain

import com.rappi.emovie.data.repository.UpcomingMovieRepository
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.uc.GetUpcomingMoviesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUpcomingMoviesUseCaseTest {

    @RelaxedMockK
    private lateinit var upcomingMovieRepository: UpcomingMovieRepository

    lateinit var getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getUpcomingMoviesUseCase = GetUpcomingMoviesUseCase(upcomingMovieRepository)
    }

    @Test
    fun `when the api does not return anything then get values from database`() = runBlocking {
        //Given
        coEvery { upcomingMovieRepository.getUpcomingMovies() } returns emptyList()

        //When
        getUpcomingMoviesUseCase()

        //Then
        coVerify(exactly = 1) { upcomingMovieRepository.getUpcomingMoviesFromDatabase() }
    }

    @Test
    fun `when the api return something then get values from api`() = runBlocking {
        //Given
        val movies = listOf(Movie(0, "test"))
        coEvery { upcomingMovieRepository.getUpcomingMovies() } returns movies

        //When
        val response = getUpcomingMoviesUseCase()

        //Then
        coVerify(exactly = 1) { upcomingMovieRepository.clearMovies() }
        coVerify(exactly = 1) { upcomingMovieRepository.insertMovies(any()) }
        coVerify(exactly = 0) { upcomingMovieRepository.getUpcomingMoviesFromDatabase() }
        assert(response == movies)
    }

}