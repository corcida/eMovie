package com.rappi.emovie.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rappi.emovie.domain.model.Genre

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String
    )

fun Genre.toDatabase() = GenreEntity(id, name)
