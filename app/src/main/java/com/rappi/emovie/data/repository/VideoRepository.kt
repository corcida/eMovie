package com.rappi.emovie.data.repository

import com.rappi.emovie.data.network.VideoRemoteDataSource
import com.rappi.emovie.domain.model.Video
import javax.inject.Inject

class VideoRepository @Inject constructor(
    private val remoteDataSource: VideoRemoteDataSource
) {

    suspend fun getVideos(movieId: Int) : List<Video> {
        val data = remoteDataSource.getVideos(movieId)
        return data.data?.results ?: emptyList()
    }

}