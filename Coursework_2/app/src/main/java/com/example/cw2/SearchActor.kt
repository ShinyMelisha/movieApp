package com.example.cw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchActor : AppCompatActivity() {
    private lateinit var actorNameTv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val editTextActorName=findViewById<EditText>(R.id.editTextActorName)
        val searchBtn=findViewById<Button>(R.id.searchBtn)
        actorNameTv=findViewById(R.id.actorTv)
//search actors
        searchBtn.setOnClickListener {
            searchActors(editTextActorName)
        }
    }
    fun searchActors(editTextActorName:EditText){
        actorNameTv.text = ""  //make the edit text empty
        val database = Room.databaseBuilder(this, MovieDatabase::class.java, "movieDataBase").build()
        val daoMovie = database.daoMovies()
        val searchActorsName = editTextActorName.text.toString().uppercase()//get the actor's name

        runBlocking {
            launch {
                val allMoviesByActors: List<Movies> = daoMovie.getActorName(searchActorsName)
                for (movie in allMoviesByActors) { //find the movies acted by the actor
                    actorNameTv.append("${movie.title}\n\n")
                }
            }
        }
        actorNameTv.text
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("movieDet", actorNameTv.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
       actorNameTv.text = savedInstanceState.getString("movieDet")
    }

}
