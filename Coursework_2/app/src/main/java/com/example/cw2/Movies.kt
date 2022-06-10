package com.example.cw2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieTable")
data class Movies (
    @PrimaryKey (autoGenerate = true) var id: Int,
    @ColumnInfo(name = "Title") var title: String?,
    @ColumnInfo(name = "Year") var year: String?,
    @ColumnInfo(name = "Rated") var rated: String?,
    @ColumnInfo(name = "Released")var released: String?,
    @ColumnInfo(name = "Runtime") var runtime: String?,
    @ColumnInfo(name = "Genre") var genre: String?,
    @ColumnInfo(name = "Director") var director: String?,
    @ColumnInfo(name = "Writer") var writer: String?,
    @ColumnInfo(name = "Actors") var actors: String?,
    @ColumnInfo(name = "Plot") var plot: String?
)
