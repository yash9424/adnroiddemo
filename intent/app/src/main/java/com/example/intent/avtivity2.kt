package com.example.intent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class avtivity2 : AppCompatActivity() {
    lateinit var back : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avtivity2)

        back = findViewById(R.id.back)
        back.setOnClickListener()
        {
            customeBack()
        }

    }

    private fun customeBack() {
        val bundle : Bundle = intent.extras!!
        val id = bundle.get("id_value")
        val language = bundle.get("language_value")
       val intent = Intent(this,MainActivity::class.java)
        Toast.makeText(applicationContext,id.toString()+"   "+language, Toast.LENGTH_LONG).show()
        startActivity(intent)
    }
}