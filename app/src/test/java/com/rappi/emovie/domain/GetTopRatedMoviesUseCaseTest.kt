package com.rappi.emovie.domain

import com.rappi.emovie.data.repository.TopRatedMovieRepository
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.uc.GetTopRatedMoviesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTopRatedMoviesUseCaseTest {

    @RelaxedMockK
    private lateinit var topRatedMovieRepository: TopRatedMovieRepository

    lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(topRatedMovieRepository)
    }

    @Test
    fun `when the api does not return anything then get values from database`() = runBlocking {
        //Given
        coEvery { topRatedMovieRepository.getTopRatedMovies() } returns emptyList()

        //When
        getTopRatedMoviesUseCase()

        //Then
        coVerify(exactly = 1) { topRatedMovieRepository.getTopRatedMoviesFromDatabase() }
    }

    @Test
    fun `when the api return something then get values from api`() = runBlocking {
        //Given
        val movies = listOf(Movie(0, "test"))
        coEvery { topRatedMovieRepository.getTopRatedMovies() } returns movies

        //When
        val response = getTopRatedMoviesUseCase()

        //Then
        coVerify(exactly = 1) { topRatedMovieRepository.clearMovies() }
        coVerify(exactly = 1) { topRatedMovieRepository.insertMovies(any()) }
        coVerify(exactly = 0) { topRatedMovieRepository.getTopRatedMoviesFromDatabase() }
        assert(response == movies)
    }

}