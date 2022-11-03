package com.rappi.emovie.domain.model

import com.rappi.emovie.data.database.entities.GenreEntity
import com.rappi.emovie.data.database.entities.TopRatedMovieEntity
import com.rappi.emovie.data.database.entities.UpcomingMovieEntity

data class Genre(
    val id: Int,
    val name: String = ""
)

fun GenreEntity.toDomain() = Genre(id, name)
