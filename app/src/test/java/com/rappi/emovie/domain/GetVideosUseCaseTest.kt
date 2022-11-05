package com.rappi.emovie.domain

import com.rappi.emovie.data.repository.VideoRepository
import com.rappi.emovie.domain.model.Video
import com.rappi.emovie.domain.uc.GetVideosUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetVideosUseCaseTest {

    @RelaxedMockK
    private lateinit var videoRepository: VideoRepository

    lateinit var getVideosUseCase: GetVideosUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getVideosUseCase = GetVideosUseCase(videoRepository)
    }

    @Test
    fun `when api returns several videos then get the first of trailer type`() = runBlocking {
        //Given
        val trailer = Video("test", "Trailer")
        val videos = listOf(Video("test", "test"), trailer)
        coEvery { videoRepository.getVideos(0) } returns videos

        //When
        val response = getVideosUseCase(0)

        //Then
        assert(response == trailer)
    }

    @Test
    fun `when api returns several videos but no trailer type then get the first one`() = runBlocking {
        //Given
        val clip = Video("test", "Clip")
        val videos = listOf(clip, Video("test", "test"))
        coEvery { videoRepository.getVideos(0) } returns videos

        //When
        val response = getVideosUseCase(0)

        //Then
        assert(response == clip)
    }

}