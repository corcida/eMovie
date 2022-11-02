package com.rappi.emovie.data.remote

import com.rappi.emovie.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TopRatedService {

    @Headers("Content-Type: application/json")
    @GET("3/movie/top_rated")
    suspend fun getTopRated(@Query("api_key") key: String = Constants.apiKey,
                            @Query("page") page: Int = 1) : Response<ResponseBody>
}