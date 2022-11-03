package com.rappi.emovie.data.network

import javax.inject.Inject

class TopRatedMovieRemoteDataSource @Inject constructor(
    private val service: TopRatedMovieService
): BaseDataSource() {

    suspend fun getTopRatedMovies() = getResult { service.getTopRated() }

}