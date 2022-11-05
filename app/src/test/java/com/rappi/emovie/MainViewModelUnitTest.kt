package com.rappi.emovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.uc.GetDateFilteredMoviesUseCase
import com.rappi.emovie.domain.uc.GetEnglishMoviesUseCase
import com.rappi.emovie.domain.uc.GetTopRatedMoviesUseCase
import com.rappi.emovie.domain.uc.GetUpcomingMoviesUseCase
import com.rappi.emovie.ui.vm.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelUnitTest {

    @RelaxedMockK
    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase

    @RelaxedMockK
    private lateinit var getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase

    @RelaxedMockK
    private lateinit var getEnglishMoviesUseCase: GetEnglishMoviesUseCase

    @RelaxedMockK
    private lateinit var getDateFilteredMoviesUseCase: GetDateFilteredMoviesUseCase

    @RelaxedMockK
    private lateinit var gson: Gson

    private lateinit var viewModel: MainViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(getTopRatedMoviesUseCase, getUpcomingMoviesUseCase,
            getEnglishMoviesUseCase, getDateFilteredMoviesUseCase, gson)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getTopRatedMoviesUseCase return movie list set on the livedata`() = runTest {
        //Given
        val movies = listOf(Movie(0, "test"))
        coEvery { getTopRatedMoviesUseCase() } returns movies

        //When
        viewModel.getTopRatedMovies()

        //Then
        assert(viewModel.model.value is MainViewModel.UiModel.TopRatedData)
    }

    @Test
    fun `when getUpcomingMoviesUseCase return movie list set on the livedata`() = runTest {
        //Given
        val movies = listOf(Movie(0, "test"))
        coEvery { getUpcomingMoviesUseCase() } returns movies

        //When
        viewModel.getUpcomingMovies()

        //Then
        assert(viewModel.model.value is MainViewModel.UiModel.UpcomingData)
    }

    @Test
    fun `when getEnglishMoviesUseCase return movie list set on the livedata`() = runTest {
        //Given
        val movies = listOf(Movie(0, "test"))
        coEvery { getEnglishMoviesUseCase() } returns movies

        //When
        viewModel.getEnglishMovies()

        //Then
        assert(viewModel.model.value is MainViewModel.UiModel.RecommendedData)
    }

    @Test
    fun `when getDateFilteredMoviesUseCase return movie list set on the livedata`() = runTest {
        //Given
        val movies = listOf(Movie(0, "test"))
        coEvery { getDateFilteredMoviesUseCase() } returns movies

        //When
        viewModel.getMoviesByDate()

        //Then
        assert(viewModel.model.value is MainViewModel.UiModel.RecommendedData)
    }

}