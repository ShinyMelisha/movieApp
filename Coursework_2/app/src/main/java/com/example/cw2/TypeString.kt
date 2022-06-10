package com.example.cw2
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class TypeString : AppCompatActivity() {

    private lateinit var substringTv: TextView
    private lateinit var title: String
    private var movieList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val substringEditText = findViewById<EditText>(R.id.editTextMovie)
        val stringBtn = findViewById<Button>(R.id.searchedBtn)
        substringTv = findViewById(R.id.textView)

        stringBtn.setOnClickListener {
            searchMovie(substringEditText)
        }
    }
    fun searchMovie(substringEditText:EditText){
        val stb = StringBuilder()
        val movieTitleString = substringEditText.text.toString()
        substringTv.text = ""
//
        val urlString = "https://www.omdbapi.com/?s=*$movieTitleString*&apikey=600f8bc4"
        val url = URL(urlString)
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection

        runBlocking {
            launch {
                withContext(Dispatchers.IO) {
                    val bf = BufferedReader(InputStreamReader(con.inputStream))
                    var readLine: String? = bf.readLine()

                    while (readLine != null) {
                        stb.append(readLine + "\n")
                        readLine = bf.readLine()
                    }
                    parseJSON(stb)
                }
            }
        }

        for (movie in movieList) {
            substringTv.append("$movie\n")
        }
    }


    private fun parseJSON(stringBuilder: java.lang.StringBuilder) {
        val json = JSONObject(stringBuilder.toString())
        val jArray: JSONArray = json.getJSONArray("Search")

        movieList.clear()

        for (movies in 0 until jArray.length()) {
            val jsonObject: JSONObject = jArray[movies] as JSONObject
            title = jsonObject.getString("Title")
            movieList.add(title)
        }


    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("substringMovie", substringTv.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        substringTv.text = savedInstanceState.getString("substringMovie")
    }
}





