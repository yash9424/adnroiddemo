package com.app.splashscreen.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.splashscreen.R
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var btnListView: Button
    lateinit var btnRecyclerView: Button
    lateinit var btnAlertDialog: Button
    lateinit var btnSnackBar: Button
    lateinit var btnPassData: Button
    lateinit var btnView: Button

    lateinit var mainLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnListView = findViewById(R.id.btnListView)
        btnRecyclerView = findViewById(R.id.btnRecyclerView)
        btnAlertDialog = findViewById(R.id.btnAlertBox)
        btnSnackBar = findViewById(R.id.btnSnackBar)
        btnPassData = findViewById(R.id.btnPassData)
        mainLayout = findViewById(R.id.mainLayout)

        btnListView.setOnClickListener(btnClickEvents)
        btnRecyclerView.setOnClickListener(btnClickEvents)
        btnAlertDialog.setOnClickListener(btnClickEvents)
        btnSnackBar.setOnClickListener(btnClickEvents)
        btnPassData.setOnClickListener(btnClickEvents)
    }


    private val btnClickEvents = View.OnClickListener { view ->
        when (view.id) {
            R.id.btnListView -> goToListActivity()
            R.id.btnRecyclerView -> goToRecyclerActivity()
            R.id.btnAlertBox -> showAlertBox()
            R.id.btnSnackBar -> showSnackBar()
            R.id.btnPassData -> passData()
        }
    }


    private fun goToListActivity() {
        val intent = Intent(this, ListViewActivity::class.java)
        startActivity(intent)
    }

    private fun passData() {
        val intent = Intent(this, PassDataActivity::class.java)
        startActivity(intent)
    }

    private fun goToRecyclerActivity() {
        val intent = Intent(this, RecyclerViewActivity::class.java)
        startActivity(intent)
    }

    private fun showAlertBox() {
        var builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage("Are You Sure You Want to exit from The App ?")
        builder.setTitle("Warning !!!")
        builder.setCancelable(false)

        builder.setPositiveButton("Yes") { dialog, which ->
            onBackPressed()
            finish()
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.cancel()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun showSnackBar() {
        val snackbar = Snackbar.make(mainLayout, "You're Showing a Snackbar", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                val snackbar =
                    Snackbar.make(mainLayout, "Yes I am still here", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        snackbar.show()
    }
}