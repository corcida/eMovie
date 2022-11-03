package com.rappi.emovie.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rappi.emovie.data.database.entities.GenreEntity

@Dao
interface GenreDao {

    @Query("SELECT * FROM genre")
    suspend fun getGenres() : List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<GenreEntity>)

    @Query("DELETE FROM genre")
    suspend fun deleteAll()

}