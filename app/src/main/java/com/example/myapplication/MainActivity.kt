package com.example.myapplication

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.moodle.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<Button>(R.id.btn_show).setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setTitle("added new user ")
        progressDialog.setMessage(" please wait")
        fun View.hideKeyboard() {
            val inputManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(windowToken, 0)
        }

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            val txt_name = binding.txtName.text
            val txt_number = binding.txtNumber.text
            val txt_address = binding.txtAddress.text
            val user = User("",txt_name.toString(),txt_number.toString(),txt_address.toString())
            if (txt_name.isEmpty() && txt_number.isEmpty() && txt_address.isEmpty()) {
                Toast.makeText(this, "enter all inputs", Toast.LENGTH_LONG).show()
            } else {
                progressDialog.show()
                db.collection("Users").add(user)
                    .addOnSuccessListener { i ->
                        progressDialog.dismiss()
                        Toast.makeText(this, "added Success " + i.id, LENGTH_LONG).show()
                        txt_number.clear()
                        txt_address.clear()
                        txt_name.clear()
                        it.hideKeyboard()
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, e.toString(), LENGTH_SHORT).show()
                    }
            }
        }
    }
}