package com.rappi.emovie.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rappi.emovie.data.database.entities.UpcomingMovieEntity

@Dao
interface UpcomingMovieDao {

    @Query("SELECT * FROM upcoming")
    suspend fun getUpcomingMovies() : List<UpcomingMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<UpcomingMovieEntity>)

    @Query("DELETE FROM upcoming")
    suspend fun deleteAll()

}