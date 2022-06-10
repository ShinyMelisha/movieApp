package com.example.cw2
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoMovies {
    @Query("Select * from movietable")
    suspend fun loadAll(): List<Movies>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(vararg movie: Movies)

    @Insert
    suspend fun insertAll(vararg movies: Movies)

    @Query("SELECT id, title from movietable WHERE UPPER(actors) LIKE '%'||:title||'%'")
    suspend fun getActorName(title: String?): List<Movies>

}