package com.example.car_rental_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class home: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_home)

        val btnhiaced = findViewById<Button>(R.id.hiaced)
        val btnprofilebut = findViewById<ImageButton>(R.id.profilebut)
        val logout = findViewById<ImageView>(R.id.logout)
        val btnhiacedeluxedetails = findViewById<Button>(R.id.hiacedeluxedetails)
        val btnmitsubishi = findViewById<Button>(R.id.mitsubishi)
        val btnbook1 = findViewById<Button>(R.id.book1)
        val btnbook2 = findViewById<Button>(R.id.book2)
        val btnbook3 = findViewById<Button>(R.id.book3)




        btnhiaced.setOnClickListener{
            val intent = Intent(this, hiacedetails::class.java)
            startActivity(intent)
        }
        btnprofilebut.setOnClickListener{
            val intent = Intent( this, profileview::class.java)
            startActivity(intent)
        }
        btnhiacedeluxedetails.setOnClickListener{
            val intent = Intent(this, hiacedeluxedetails::class.java)
            startActivity(intent)
        }
        btnmitsubishi.setOnClickListener {
            val intent = Intent(this, mitsubishi::class.java)
            startActivity(intent)
        }
        btnbook1.setOnClickListener {
            val intent = Intent(this, book::class.java)
            startActivity(intent)
        }
        btnbook2.setOnClickListener {
            val intent = Intent(this, book::class.java)
            startActivity(intent)
        }
        btnbook3.setOnClickListener {
            val intent = Intent(this, book::class.java)
            startActivity(intent)
        }


        logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }
    }


}

