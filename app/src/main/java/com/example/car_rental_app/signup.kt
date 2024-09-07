package com.example.car_rental_app

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val auth = FirebaseAuth.getInstance()
        val btnsignup = findViewById<Button>(R.id.signup)

        val edtsignupemail = findViewById<EditText>(R.id.signupname)
        val edtsignuppass = findViewById<EditText>(R.id.signuppass)
        val edtsignupdob = findViewById<EditText>(R.id.signupdob)
        val edtsignupaddress = findViewById<EditText>(R.id.signupaddress)
        val edtsignuplicense = findViewById<EditText>(R.id.signuplicense)
        val edtsignupmobile = findViewById<EditText>(R.id.signupmobile)

        val termscheckbox = findViewById<CheckBox>(R.id.termsCheckBox)
        val termsTextView = findViewById<TextView>(R.id.terms)

        // Set the terms and conditions text with clickable span
        val termsText = getString(R.string.terms_and_condition)
        val spannableString = SpannableString(Html.fromHtml(termsText, Html.FROM_HTML_MODE_LEGACY))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle the terms and conditions click
                val intent = Intent(this@signup, termsandcondition::class.java)
                startActivity(intent)
            }
        }

        val start = termsText.indexOf("terms and conditions")
        val end = start + "terms and conditions".length
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        termsTextView.text = spannableString
        termsTextView.movementMethod = LinkMovementMethod.getInstance()

        // Disable the sign-up button initially
        btnsignup.isEnabled = false

        // Enable the sign-up button only if the terms checkbox is checked
        termscheckbox.setOnCheckedChangeListener { _, isChecked ->
            btnsignup.isEnabled = isChecked
        }

        // Set up DatePickerDialog for Date of Birth field
        edtsignupdob.setOnClickListener {
            // Get the current date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Show DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Format the selected date and set it to the EditText
                    val selectedDate = "${selectedMonth + 1}/$selectedDay/$selectedYear"
                    edtsignupdob.setText(selectedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        btnsignup.setOnClickListener {
            val email = edtsignupemail.text.toString()
            val password = edtsignuppass.text.toString()
            val dob = edtsignupdob.text.toString()
            val address = edtsignupaddress.text.toString()
            val license = edtsignuplicense.text.toString()
            val mobile = edtsignupmobile.text.toString()

            if (email.isEmpty()) {
                edtsignupemail.error = "Invalid"
            } else if (password.isEmpty()) {
                edtsignuppass.error = "Invalid"
            } else if (dob.isEmpty()) {
                edtsignupdob.error = "Invalid"
            } else if (address.isEmpty()) {
                edtsignupaddress.error = "Invalid"
            } else if (license.isEmpty()) {
                edtsignuplicense.error = "Invalid"
            } else if (mobile.isEmpty()) {
                edtsignupmobile.error = "Invalid"
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { user ->
                        val userId = user.user?.uid.toString()
                        Firebase.database.getReference("Users")
                            .child(userId).apply {
                                child("email").setValue(edtsignupemail.text.toString())
                                child("dob").setValue(edtsignupdob.text.toString())
                                child("address").setValue(edtsignupaddress.text.toString())
                                child("license").setValue(edtsignuplicense.text.toString())
                                child("mobile").setValue(edtsignupmobile.text.toString())
                            }

                        // Navigate to the main activity after successful sign-up
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { exception ->
                        // Handle sign-up failure
                        // Show a toast or dialog with the error message
                    }
            }
        }
    }
}
