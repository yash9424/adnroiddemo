package com.app.splashscreen.activity

import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.splashscreen.R
import com.app.splashscreen.adapter.CustomListAdapter

class ListViewActivity : AppCompatActivity() {

    val language = arrayOf("C", "C++", "Java", "Python", "Kotlin", "Android")

    val description = arrayOf(
        "TEST 1 description",
        "TEST 2 description",
        "TEST 3 description",
        "TEST 4 description",
        "TEST 5 description",
        "TEST 6 description",
    )

    val imageId = arrayOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val listView = findViewById<ListView>(R.id.myListView)

        val myListAdapter = CustomListAdapter(this, language, description, imageId)
        listView.adapter = myListAdapter

        listView.setOnItemClickListener { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            Toast.makeText(
                this,
                "Click on item at $itemAtPos",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}