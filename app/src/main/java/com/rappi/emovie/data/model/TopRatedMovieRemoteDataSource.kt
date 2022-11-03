package com.rappi.emovie.data.model

import javax.inject.Inject

class TopRatedMovieRemoteDataSource @Inject constructor(
    private val service: TopRatedMovieService
): BaseDataSource() {

    suspend fun getTopRatedMovies() = getResult { service.getTopRated() }

}