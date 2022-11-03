package com.rappi.emovie.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rappi.emovie.domain.model.Movie

@Entity(tableName = "top_rated")
data class TopRatedMovie(
    @PrimaryKey
    val id: String,
    val title: String,
    val overview: String,
    val original_language: String,
    val genre_ids: List<Int>,
    val poster_path: String,
    val vote_average: Float
    )

fun Movie.toDatabase() = TopRatedMovie(id, title, overview,
    original_language, genre_ids, poster_path, vote_average)


