package com.rappi.emovie.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.rappi.emovie.data.database.converters.IntListConverter
import com.rappi.emovie.domain.model.Movie

@Entity(tableName = "top_rated")
@TypeConverters(IntListConverter::class)
data class TopRatedMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val original_language: String,
    val release_date: String,
    val genre_ids: List<Int>,
    val poster_path: String,
    val vote_average: Float
    )

fun Movie.toDatabase() = TopRatedMovieEntity(id, title, overview,
    original_language, release_date, genre_ids, poster_path,  vote_average)


