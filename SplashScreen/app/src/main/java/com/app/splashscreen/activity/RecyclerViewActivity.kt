package com.app.splashscreen.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.splashscreen.R
import com.app.splashscreen.adapter.RecyclerViewAdapter
import com.app.splashscreen.model.SeriesList

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(
            this,
            1,
            GridLayoutManager.VERTICAL,
            false
        )

        val adapter = RecyclerViewAdapter(SeriesList.getModel())
        recyclerView.adapter = adapter
    }

}