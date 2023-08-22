package com.example.passdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var submit : Button
    lateinit var name : EditText
    lateinit var email : EditText
    lateinit var pwd : EditText
    lateinit var num : EditText
    lateinit var addr : EditText
    private lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        submit = findViewById(R.id.submit)
        name = findViewById(R.id.name)
        pwd = findViewById(R.id.pwd)
        email = findViewById(R.id.email)
        num = findViewById(R.id.num)
        addr = findViewById(R.id.addr)
        dbReference = FirebaseDatabase.getInstance().getReference("StudentData")

        submit.setOnClickListener()
        {
            customeSubmit()
        }

    }

    private fun customeSubmit() {
        val stname = name.text.toString()
        val stemail = email.text.toString()
        val stpwd = pwd.text.toString()
        val stnum = num.text.toString()
        val staddr = addr.text.toString()

        if(stname.isBlank() && stemail.isBlank() && stpwd.isBlank() && stnum.isBlank() && staddr.isBlank())
        {
            Toast.makeText(this,"Please Fill All Field",Toast.LENGTH_SHORT).show()
        }
        else
        {
            val stID = dbReference.push().key!!

            val StudentData = PassDataModel(stID,stname,stemail,stpwd,stnum,staddr)
            dbReference.child(stID).setValue(StudentData)
                .addOnCompleteListener {
                    Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Error ${it.message}",Toast.LENGTH_SHORT).show()
                }
        }
    }
}