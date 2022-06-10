package com.example.cw2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchMovie : AppCompatActivity() {
    private lateinit var title: String
    private lateinit var year: String
    private lateinit var rated: String
    private lateinit var released: String
    private lateinit var runtime: String
    private lateinit var genre: String
    private lateinit var director: String
    private lateinit var writer: String
    private lateinit var actors: String
    private lateinit var plot: String
    private lateinit var movieTv: TextView

@SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var editTextMovie=findViewById<EditText>(R.id.editTextMovieName)
        val retrieveBtn=findViewById<Button>(R.id.retrieveBtn)
        val saveMovieBtn=findViewById<Button>(R.id.saveBtn)
        movieTv=findViewById(R.id.movieTv)

        retrieveBtn.setOnClickListener {
            retrieveMovieDet(editTextMovie)
        }
        saveMovieBtn.setOnClickListener {
            saveMovieToDb()
        }
    }
    fun retrieveMovieDet(editTextMovie:EditText){
        val stb = StringBuilder()
        val movieNameEditTxt = editTextMovie.text.toString()   //user input movie name

            val urlString = "https://www.omdbapi.com/?t=$movieNameEditTxt&apikey=600f8bc4"//api url
            val url = URL(urlString)
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection

            runBlocking {
                launch {
                    withContext(Dispatchers.IO) {
                        val bf = BufferedReader(InputStreamReader(con.inputStream))//bufferedreader reads the characters
                        var line: String? = bf.readLine()

                        while (line != null) {
                            stb.append(line + "\n")
                            line = bf.readLine()
                        }
                        parseJSON(stb)
                    }

            }
        }

       movieTv.text = "Title: " + title + "\nYear: " + year + "\nRated: " + rated +
                "\nReleased: " + released + "\nRuntime: " + runtime + "\nGenre: " + genre +
                "\nDirector: " + director + "\nWriter: " + writer + "\nActors: " + actors + "\n\nPlot: " + plot
    }
    private fun parseJSON(stb: StringBuilder) {
        val json = JSONObject(stb.toString())
        title=json["Title"].toString()
        year=json["Year"].toString()
        rated=json["Rated"].toString()
        released=json["Released"].toString()
        runtime=json["Runtime"].toString()
        genre=json["Genre"].toString()
        director=json["Director"].toString()
        writer=json["Writer"].toString()
        actors=json["Actors"].toString()
        plot=json["Plot"].toString()
    }
    fun saveMovieToDb(){
        val database = Room.databaseBuilder(this, MovieDatabase::class.java, "movieDataBase").build()//get the database
        val daoMovie = database.daoMovies()

        runBlocking {
            launch {
                val movies: List<Movies> = daoMovie.loadAll()
                val noOfMovies = movies.size
                val saveTheMovie = Movies(noOfMovies+1, title, year, rated, released, runtime, genre, director, writer, actors, plot)

                daoMovie.addMovies(saveTheMovie)
                Toast.makeText(applicationContext, "Movie added to the Database.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("movieDet", movieTv.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        movieTv.text = savedInstanceState.getString("movieDet")
    }


}