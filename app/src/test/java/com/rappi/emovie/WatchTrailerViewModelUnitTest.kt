package com.rappi.emovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.rappi.emovie.domain.model.Video
import com.rappi.emovie.domain.uc.GetVideosUseCase
import com.rappi.emovie.ui.vm.WatchTrailerViewModel
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
class WatchTrailerViewModelUnitTest {

    @RelaxedMockK
    private lateinit var getVideosUseCase: GetVideosUseCase

    @RelaxedMockK
    private lateinit var gson: Gson

    private lateinit var viewModel: WatchTrailerViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = WatchTrailerViewModel(getVideosUseCase, gson)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getVideosUseCase return video list set on the livedata`() = runTest {
        //Given
        val video = Video("0", "test")
        coEvery { getVideosUseCase(0) } returns video

        //When
        viewModel.getVideos(0)

        //Then
        assert(viewModel.model.value is WatchTrailerViewModel.UiModel.VideoData)
    }

}