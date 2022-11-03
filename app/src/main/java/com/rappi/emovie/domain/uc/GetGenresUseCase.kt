package com.rappi.emovie.domain.uc

import com.rappi.emovie.data.database.entities.toDatabase
import com.rappi.emovie.data.repository.GenreRepository
import com.rappi.emovie.domain.model.Genre
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: GenreRepository
) {
    suspend operator fun invoke(ids: List<Int>):List<Genre>{
        var genres = repository.getGenresFromDatabase()
        return genres.filter{ ids.contains(it.id) }.ifEmpty {
            genres = repository.getGenres()
            if (genres.isNotEmpty()) {
                repository.clearGenres()
                repository.insertGenres(genres.map { it.toDatabase() })
                genres.filter{ ids.contains(it.id) }
            } else {
                emptyList()
            }
        }
    }

}