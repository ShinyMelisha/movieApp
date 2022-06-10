package com.example.cw2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movies::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun daoMovies(): DaoMovies
}



