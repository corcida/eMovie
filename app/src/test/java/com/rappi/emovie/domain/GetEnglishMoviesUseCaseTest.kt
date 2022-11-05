package com.rappi.emovie.domain

import com.rappi.emovie.data.repository.TopRatedMovieRepository
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.uc.GetEnglishMoviesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetEnglishMoviesUseCaseTest {

    @RelaxedMockK
    private lateinit var topRatedMovieRepository: TopRatedMovieRepository

    lateinit var getEnglishMoviesUseCase: GetEnglishMoviesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getEnglishMoviesUseCase = GetEnglishMoviesUseCase(topRatedMovieRepository)
    }

    @Test
    fun `when the database has some movie data then return only english movies from database`() = runBlocking {
        //Given
        val englishMovies = listOf(Movie(0, "test", "","en"))
        val movies = listOf(Movie(1, "test"))
        movies.plus(englishMovies)
        coEvery { topRatedMovieRepository.getTopRatedMoviesFromDatabase() } returns movies

        //When
        val response = getEnglishMoviesUseCase()

        //Then
        assert(response == englishMovies)
    }

}