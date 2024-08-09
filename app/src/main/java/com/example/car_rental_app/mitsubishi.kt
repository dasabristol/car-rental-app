package com.example.car_rental_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class mitsubishi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mitsubishi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnmitsubishihome = findViewById<Button>(R.id.mitsubishihome)
        val btnbook6 = findViewById<Button>(R.id.book6)

        btnmitsubishihome.setOnClickListener {
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }
        btnbook6.setOnClickListener {
            val intent = Intent(this, book::class.java)
            startActivity(intent)
        }
    }
}