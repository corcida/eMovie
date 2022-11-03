package com.rappi.emovie.data.network

import com.rappi.emovie.data.model.TopRatedMovieData
import com.rappi.emovie.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UpcomingMovieService {

    @Headers("Content-Type: application/json")
    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("api_key") key: String = Constants.apiKey,
                            @Query("page") page: Int = 1) : Response<TopRatedMovieData>
}