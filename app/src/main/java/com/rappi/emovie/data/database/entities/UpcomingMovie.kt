package com.rappi.emovie.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming")
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
