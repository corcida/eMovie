package com.rappi.emovie.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.rappi.emovie.data.database.converters.IntListConverter
import com.rappi.emovie.domain.model.Genre
import com.rappi.emovie.domain.model.Movie

@Entity(tableName = "genre")
@TypeConverters(IntListConverter::class)
data class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String
    )

fun Genre.toDatabase() = GenreEntity(id, name)
