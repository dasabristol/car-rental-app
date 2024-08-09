package com.example.car_rental_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import android.widget.ArrayAdapter;
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class book : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book)


        var database = FirebaseDatabase.getInstance()
        var auth = FirebaseAuth.getInstance()

        val reference = database.getReference("Bookings")
        val userId = auth.currentUser?.uid.toString()



        val btnbookhome = findViewById<Button>(R.id.bookhome)
        val btnSend = findViewById<Button>(R.id.send)
        val edtusernamebook = findViewById<EditText>(R.id.usernamebook)
        val edtaddressbook = findViewById<EditText>(R.id.addressbook)
        val edtmobilebook = findViewById<EditText>(R.id.mobilebook)
        val edtlicensebook = findViewById<EditText>(R.id.licensebook)

        val edtdobbook = findViewById<Spinner>(R.id.dobbook)

        val adapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtdobbook.adapter = adapter





        btnSend.setOnClickListener {
            Log.d("FOoo", "Bar")

            reference.child(userId).child(reference.push().key.toString())
                .apply {
                    child("name").setValue(edtusernamebook.text.toString())
                    child("car").setValue(edtdobbook.selectedItem.toString())
                    child("address").setValue(edtaddressbook.text.toString())
                    child("mobilenumber").setValue(edtmobilebook.text.toString())
                    child("license").setValue(edtlicensebook.text.toString())
                }


        }


        btnbookhome.setOnClickListener {
            val intent = Intent(this, home::class.java)
            startActivity(intent)
        }

    }
}