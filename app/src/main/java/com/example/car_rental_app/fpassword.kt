package com.example.car_rental_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class fpassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fpassword)


        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Get references to the UI elements
        val emailEditText = findViewById<EditText>(R.id.email_forgotpassword)
        val submitButton = findViewById<Button>(R.id.submit_button)
//        val btnloginpage = findViewById<Button>(R.id.loginpage)

        // Set onClick listener for the submit button
        submitButton.setOnClickListener {
            onSubmitClick(emailEditText)
        }
    }

    private fun onSubmitClick(emailEditText: EditText) {
        // Get the email from EditText
        val email = emailEditText.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            return
        }

        // Send password reset email
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to send password reset email", Toast.LENGTH_SHORT).show()
                    // Optionally handle specific errors
                    task.exception?.let { exception ->
                        Toast.makeText(this, "Error: ${exception.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
                }
            }

        /* btnloginpage.setOnClickListener {
             val intent = Intent(this, MainActivity::class.java)
             startActivity(intent)
         }*/
    }
}