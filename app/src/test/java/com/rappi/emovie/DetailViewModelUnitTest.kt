package com.rappi.emovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.rappi.emovie.domain.model.Genre
import com.rappi.emovie.domain.uc.GetGenresUseCase
import com.rappi.emovie.ui.vm.DetailViewModel
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
class DetailViewModelUnitTest {

    @RelaxedMockK
    private lateinit var getGenresUseCase: GetGenresUseCase

    @RelaxedMockK
    private lateinit var gson: Gson

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = DetailViewModel(getGenresUseCase, gson)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getGenresUseCase return genre list set on the livedata`() = runTest {
        //Given
        val genres = listOf(Genre(0, "test"))
        coEvery { getGenresUseCase(listOf(0)) } returns genres

        //When
        viewModel.getGenres(listOf(0))

        //Then
        assert(viewModel.model.value is DetailViewModel.UiModel.Genres)
    }

}