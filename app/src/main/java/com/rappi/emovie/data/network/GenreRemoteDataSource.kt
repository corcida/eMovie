package com.rappi.emovie.data.network

import javax.inject.Inject

class GenreRemoteDataSource @Inject constructor(
    private val service: GenreService
): BaseDataSource() {

    suspend fun getGenres() = getResult { service.getGenres() }

}