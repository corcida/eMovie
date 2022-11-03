package com.rappi.emovie.domain.model

import com.rappi.emovie.data.database.entities.TopRatedMovieEntity
import com.rappi.emovie.data.database.entities.UpcomingMovieEntity

data class Movie(
    val id: Int,
    val title: String = "",
    val overview: String = "",
    val original_language: String = "",
    val release_date: String = "",
    val genre_ids: List<Int> = emptyList(),
    val poster_path: String = "",
    val vote_average: Float = 0.0f
)

fun TopRatedMovieEntity.toDomain() = Movie(id, title, overview,
    original_language, release_date, genre_ids, poster_path, vote_average)

fun UpcomingMovieEntity.toDomain() = Movie(id, title, overview,
    original_language, release_date, genre_ids, poster_path, vote_average)
