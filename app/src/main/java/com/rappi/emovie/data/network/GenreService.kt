package com.rappi.emovie.data.network

import com.rappi.emovie.data.model.GenreData
import com.rappi.emovie.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GenreService {

    @Headers("Content-Type: application/json")
    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") key: String = Constants.apiKey)
    : Response<GenreData>

}