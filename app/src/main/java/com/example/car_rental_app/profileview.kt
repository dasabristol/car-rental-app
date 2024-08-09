package com.example.car_rental_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class profileview : AppCompatActivity() {

    private lateinit var usernameProfile: TextView
    private lateinit var dobProfile: TextView
    private lateinit var addressProfile: TextView
    private lateinit var mobileProfile: TextView
    private lateinit var licenseProfile: TextView
    private lateinit var btnProfileEdit: Button
    private lateinit var btnProfileHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profileview)

        // Initialize views
        usernameProfile = findViewById(R.id.usernameprofile)
        dobProfile = findViewById(R.id.dobprofile)
        addressProfile = findViewById(R.id.addressprofile)
        mobileProfile = findViewById(R.id.mobileprofile)
        licenseProfile = findViewById(R.id.licenseprofile)
        btnProfileEdit = findViewById(R.id.profileedit)
        btnProfileHome = findViewById(R.id.profilehome)

        // Fetch and display user profile data
        val database: DatabaseReference = FirebaseDatabase.getInstance().reference
        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid ?: return // Ensure user is logged in

        val userRef = database.child("Users").child(userId)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    usernameProfile.text = snapshot.child("username").value.toString()
                    dobProfile.text = snapshot.child("dob").value.toString()
                    addressProfile.text = snapshot.child("address").value.toString()
                    mobileProfile.text = snapshot.child("mobile").value.toString()
                    licenseProfile.text = snapshot.child("license").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
            }
        })

        // Handle button clicks
        btnProfileEdit.setOnClickListener {
            val intent = Intent(this, editprofile::class.java)
            startActivity(intent)
        }

        btnProfileHome.setOnClickListener {
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }
    }
}
