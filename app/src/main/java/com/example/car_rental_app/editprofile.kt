package com.example.car_rental_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
                    // Optionally, show a success message
                    // For example, using a Toast:
                    // Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()

                    // Navigate to profile view
                    val intent = Intent(this, profileview::class.java)
                    startActivity(intent)
                    finish() // Optionally finish the current activity
                } else {
                    // Handle possible errors
                    // For example, using a Toast:
                    // Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}