package com.example.projetkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button = findViewById<Button>(R.id.toMain)
        button.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            applicationContext.startActivity(intent)
        }
        val listDives = findViewById<LinearLayout>(R.id.DiveList)
        val oneDive = findViewById<LinearLayout>(R.id.dive)
        val diveDate = findViewById<LinearLayout>(R.id.diveDate)
        val divePlace = findViewById<LinearLayout>(R.id.divePlace)
        val diveRegister = findViewById<LinearLayout>(R.id.diveRegister)
        val diveSubmit = findViewById<Button>(R.id.diveSubmit)
        val dataDate = findViewById<TextView>(R.id.dataDate)
        val dataPlace = findViewById<TextView>(R.id.dataPlace)
        val dataSlot = findViewById<TextView>(R.id.dataSlot)
    }
}
