package com.example.car_rental_app

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class editprofile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        val btnSaveProfile = findViewById<Button>(R.id.saveprofile)
        val edtUsernameEdit = findViewById<EditText>(R.id.usernameedit)
        val edtDobEdit = findViewById<EditText>(R.id.dobedit)
        val edtAddressEdit = findViewById<EditText>(R.id.addressedit)
        val edtMobileEdit = findViewById<EditText>(R.id.mobileedit)
        val edtLicenseEdit = findViewById<EditText>(R.id.licenseedit)

        val database = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid ?: return // Ensure user is logged in

        // Fetch and populate existing profile data
        val userRef = database.getReference("Users").child(userId)
        userRef.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    edtUsernameEdit.setText(snapshot.child("username").value.toString())
                    edtDobEdit.setText(snapshot.child("dob").value.toString())
                    edtAddressEdit.setText(snapshot.child("address").value.toString())
                    edtMobileEdit.setText(snapshot.child("mobile").value.toString())
                    edtLicenseEdit.setText(snapshot.child("license").value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })

        // DatePickerDialog for Date of Birth
        edtDobEdit.setOnClickListener {
            // Get the current date to show as default
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Create and show the DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Set the selected date to the EditText
                    val selectedDate = "${selectedMonth + 1}/$selectedDay/$selectedYear"
                    edtDobEdit.setText(selectedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        btnSaveProfile.setOnClickListener {
            val updatedUsername = edtUsernameEdit.text.toString()
            val updatedDob = edtDobEdit.text.toString()
            val updatedAddress = edtAddressEdit.text.toString()
            val updatedMobile = edtMobileEdit.text.toString()
            val updatedLicense = edtLicenseEdit.text.toString()

            // Update user data in Firebase
            val userUpdates = mapOf(
                "username" to updatedUsername,
                "dob" to updatedDob,
                "address" to updatedAddress,
                "mobile" to updatedMobile,
                "license" to updatedLicense
            )

            userRef.updateChildren(userUpdates).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Optionally, show a success message or navigate to another activity
                    val intent = Intent(this, profileview::class.java)
                    startActivity(intent)
                    finish() // Optionally finish the current activity
                } else {
                    // Handle possible errors (e.g., using a Toast)
                }
            }
        }
    }
}
