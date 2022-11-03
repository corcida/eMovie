package com.rappi.emovie.data.model

import com.rappi.emovie.data.entities.TopRatedMovieData
import com.rappi.emovie.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TopRatedMovieService {

    @Headers("Content-Type: application/json")
    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("api_key") key: String = Constants.apiKey,
                            @Query("page") page: Int = 1) : Response<TopRatedMovieData>
}