package com.rappi.emovie.data.network

import com.rappi.emovie.data.model.VideoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface VideoService {

    @Headers("Content-Type: application/json")
    @GET("movie/{video}/videos")
    suspend fun getVideos(@Path("video" ) id : Int)
    : Response<VideoData>

}