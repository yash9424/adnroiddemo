package com.app.splashscreen.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.app.splashscreen.R

class ReceiverActivity : AppCompatActivity() {

    lateinit var tvName: TextView
    lateinit var tvRoll: TextView
    lateinit var tvEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        tvName = findViewById(R.id.tvNameData)
        tvRoll = findViewById(R.id.tvEnrollData)
        tvEmail = findViewById(R.id.tvEmailData)

        val bundle = intent.extras

        if (bundle != null) {
            tvName.text = "${bundle.getString("studentName")}"
            tvRoll.text = "${bundle.getString("roll")}"
            tvEmail.text = "${bundle.getString("email")}"
        }
    }
}