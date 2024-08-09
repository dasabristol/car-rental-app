package com.example.car_rental_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class hiacedetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hiacedetails)

        val  btnhiacedhome = findViewById<Button>(R.id.hiacedhome)
        val btnbook4 = findViewById<Button>(R.id.book4)

        btnhiacedhome.setOnClickListener {
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }
        btnbook4.setOnClickListener {
            val intent = Intent(this, book::class.java)
            startActivity(intent)
        }
    }
}