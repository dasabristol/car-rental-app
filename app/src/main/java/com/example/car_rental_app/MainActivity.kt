package com.example.car_rental_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser?.uid.toString().isNullOrEmpty()){
            Log.d("Hadueken", auth.currentUser?.uid.toString())
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }

        setContentView(R.layout.activity_main)




        val btnLogin = findViewById<Button>(R.id.login)
        val btnfpassword = findViewById<TextView>(R.id.fpassword)
        val btnupsign = findViewById<TextView>(R.id.upsign)
        val edtusername = findViewById<EditText>(R.id.username)
        val edtpassword = findViewById<EditText>(R.id.password)


        btnLogin.setOnClickListener {
            val username = edtusername.text.toString()
            val password = edtpassword.text.toString()


            if (username.isEmpty()){
                edtusername.error="Invalid"
            }
            else if(password.isEmpty()){
                edtpassword.error="Invalid"
            }
            else{

                auth.signInWithEmailAndPassword(username, password)
                    .addOnSuccessListener {
                        val intent = Intent(this, home::class.java)
                        startActivity(intent)

                    }.addOnFailureListener{
                        Toast.makeText( this, "INVALID USER", Toast.LENGTH_LONG).show()
                    }
            }

        }
        btnfpassword.setOnClickListener {
            val intent = Intent(this, fpassword::class.java)
            startActivity(intent)
        }
        btnupsign.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)

        }
    }
}