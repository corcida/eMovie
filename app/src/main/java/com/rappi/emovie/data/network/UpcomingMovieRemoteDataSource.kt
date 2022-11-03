package com.rappi.emovie.data.network

import javax.inject.Inject

class UpcomingMovieRemoteDataSource @Inject constructor(
    private val service: UpcomingMovieService
): BaseDataSource() {

    suspend fun getUpcomingMovies() = getResult { service.getUpcoming() }

}