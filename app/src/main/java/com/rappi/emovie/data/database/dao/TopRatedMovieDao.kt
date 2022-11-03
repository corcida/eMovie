package com.rappi.emovie.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rappi.emovie.data.database.entities.TopRatedMovieEntity

@Dao
interface TopRatedMovieDao {

    @Query("SELECT * FROM top_rated")
    suspend fun getTopRatedMovies() : List<TopRatedMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<TopRatedMovieEntity>)

    @Query("DELETE FROM top_rated")
    suspend fun deleteAll()

}