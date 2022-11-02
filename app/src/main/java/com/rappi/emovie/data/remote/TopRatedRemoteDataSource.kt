package com.rappi.emovie.data.remote

import javax.inject.Inject

class TopRatedRemoteDataSource @Inject constructor(
    private val petService: TopRatedService
): BaseDataSource() {
}