package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.moodle.Person
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val database = Firebase.database
    private val myRef = database.reference
    var count = 0
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<Button>(R.id.btn_show).setOnClickListener {

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue()
                    binding.txtPersons.text = value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            val txt_name = binding.txtName.text
            val txt_number = binding.txtNumber.text
            val txt_address = binding.txtAddress.text

            val person =
                Person("", txt_name.toString(), txt_number.toString(), txt_address.toString())

            if (txt_name.isNotEmpty() && txt_number.isNotEmpty() && txt_address.isNotEmpty()) {

                myRef.child("person").child(count.toString()).setValue(person)
                count++

                Toast.makeText(this, "added Success " + count.toString(), LENGTH_LONG).show()
                txt_number.clear()
                txt_address.clear()
                txt_name.clear()

            } else {
                Toast.makeText(this, "enter all inputs", Toast.LENGTH_LONG).show()

            }
        }
    }
}