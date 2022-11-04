package com.rappi.emovie.domain.uc

import com.rappi.emovie.data.repository.VideoRepository
import com.rappi.emovie.domain.model.Video
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(
    private val repository: VideoRepository
) {

    suspend operator fun invoke(id: Int) : Video? {
        val videos = repository.getVideos(id)
        return videos.firstOrNull { it.type == "Trailer" } ?: videos.firstOrNull()
    }

}