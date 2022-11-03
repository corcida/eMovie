package com.rappi.emovie.domain.model

import com.rappi.emovie.data.database.entities.TopRatedMovie
import com.rappi.emovie.data.database.entities.UpcomingMovie

data class Movie(
    val id: String,
    val title: String = "",
    val overview: String = "",
    val original_language: String = "",
    val release_date: String = "",
    val genre_ids: List<Int> = emptyList(),
    val poster_path: String = "",
    val vote_average: Float = 0.0f
)

fun TopRatedMovie.toDomain() = Movie(id, title, overview,
    original_language, release_date, genre_ids, poster_path, vote_average)

fun UpcomingMovie.toDomain() = Movie(id, title, overview,
    original_language, release_date, genre_ids, poster_path, vote_average)
