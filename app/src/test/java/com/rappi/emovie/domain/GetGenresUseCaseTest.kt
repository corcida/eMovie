package com.rappi.emovie.domain

import com.rappi.emovie.data.repository.GenreRepository
import com.rappi.emovie.domain.model.Genre
import com.rappi.emovie.domain.uc.GetGenresUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetGenresUseCaseTest {

    @RelaxedMockK
    private lateinit var genreRepository: GenreRepository

    lateinit var getGenresUseCase: GetGenresUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getGenresUseCase = GetGenresUseCase(genreRepository)
    }

    @Test
    fun `when database already has the data then get values from database`() = runBlocking {
        //Given
        val genres = listOf(Genre(0, "test"))
        coEvery { genreRepository.getGenresFromDatabase() } returns genres

        //When
        val response = getGenresUseCase(listOf(0))

        //Then
        assert(response == genres)
    }

    @Test
    fun `when database do not have the data then get values from api`() = runBlocking {
        //Given
        val genresInDatabase = listOf(Genre(0, "test"))
        val genresInApi = listOf(Genre(1, "test"))
        coEvery { genreRepository.getGenresFromDatabase() } returns genresInDatabase
        coEvery { genreRepository.getGenres() } returns genresInApi

        //When
        val response = getGenresUseCase(listOf(1))

        //Then
        assert(response == genresInApi)
    }

}