package com.rappi.emovie.data.network

import javax.inject.Inject

class VideoRemoteDataSource @Inject constructor(
    private val service: VideoService
): BaseDataSource() {

    suspend fun getVideos(id: Int) = getResult { service.getVideos(id) }

}