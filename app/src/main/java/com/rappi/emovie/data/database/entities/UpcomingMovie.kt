package com.rappi.emovie.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.rappi.emovie.data.database.converters.IntListConverter
import com.rappi.emovie.domain.model.Movie

@Entity(tableName = "upcoming")
@TypeConverters(IntListConverter::class)
data class UpcomingMovie(
    @PrimaryKey
    val id: String,
    val title: String,
    val overview: String,
    val original_language: String,
    val genre_ids: List<Int>,
    val poster_path: String,
    val vote_average: Float
    )

fun Movie.toUpcomingDatabase() = UpcomingMovie(id, title, overview,
    original_language, genre_ids, poster_path, vote_average)
