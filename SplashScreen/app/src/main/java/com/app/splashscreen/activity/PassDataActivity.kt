package com.app.splashscreen.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.app.splashscreen.R

class PassDataActivity : AppCompatActivity() {

    //edit text for input the data
    lateinit var etName: EditText
    lateinit var etEnroll: EditText
    lateinit var etEmail: EditText

    // button for pass the data to another activity
    lateinit var btnSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_data)

        etName = findViewById(R.id.etName)
        etEnroll = findViewById(R.id.etEnRoll)
        etEmail = findViewById(R.id.etEmail)

        btnSend = findViewById(R.id.btnSend)

        btnSend.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("studentName", etName.text.toString())
            bundle.putString("roll", etEnroll.text.toString())
            bundle.putString("email", etEmail.text.toString())

            val intent = Intent(this, ReceiverActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}