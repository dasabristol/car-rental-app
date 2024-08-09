package com.example.car_rental_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.EditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

class signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val auth = FirebaseAuth.getInstance()
        val btnsignup = findViewById<Button>(R.id.signup)


        val edtsignupname = findViewById<EditText>(R.id.signupname)
        val edtsignuppass = findViewById<EditText>(R.id.signuppass)
        val edtsignupdob = findViewById<EditText>(R.id.signupdob)
        val edtsignupaddress = findViewById<EditText>(R.id.signupaddress)
        val edtsignuplicense = findViewById<EditText>(R.id.signuplicense)
        val edtsignupmobile = findViewById<EditText>(R.id.signupmobile)

        btnsignup.setOnClickListener {
            val name = edtsignupname.text.toString()
            val password = edtsignuppass.text.toString()
            val dob = edtsignupdob.text.toString()
            val address = edtsignupaddress.text.toString()
            val license = edtsignuplicense.text.toString()
            val mobile = edtsignupmobile.text.toString()

            if (name.isEmpty()) {
                edtsignupname.error = "Invalid"
            } else if (password.isEmpty()) {
                edtsignuppass.error = "Invalid"
            } else if (dob.isEmpty()) {
                edtsignupdob.error = "Invalid"
            } else if (address.isEmpty()) {
                edtsignupdob.error = "Invalid"
            } else if (license.isEmpty()) {
                edtsignupaddress.error = "Invalid"
            } else if (mobile.isEmpty()){
                edtsignupmobile.error = "Invalid"
            }else {

                auth.createUserWithEmailAndPassword(name, password)
                    .addOnSuccessListener { user ->
                        val userId = user.user?.uid.toString()
                        Firebase.database.getReference("Users")
                            .child(userId).apply {
                                child("name").setValue(edtsignupname.text.toString())
                                child("dob").setValue(edtsignupdob.text.toString())
                                child("address").setValue(edtsignupaddress.text.toString())
                                child("license").setValue(edtsignuplicense.text.toString())
                                child("mobile").setValue(edtsignupmobile.text.toString())
                            }

                    }
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}