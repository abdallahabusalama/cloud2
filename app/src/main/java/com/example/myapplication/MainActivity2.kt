@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.example.myapplication
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.UserAdapter
import com.example.myapplication.databinding.ActivityAdapterUserBinding
import com.example.myapplication.moodle.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity() {

    lateinit var binding: ActivityAdapterUserBinding
    private lateinit var recycleView: RecyclerView
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdapterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var data = ArrayList<User>()


        recycleView = findViewById(R.id.listView)
        ArrayAdapter(this, android.R.layout.activity_list_item, data)
        recycleView.layoutManager = LinearLayoutManager(this)

        db.collection("Users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        data.clone()
                        val old_user = User(document.id,document.get("name") as String,document.get("number") as String,document.get("address") as String)
                        data.add(old_user)
                        Log.e("TAG", document.id + " => " + document.data)
                    }
                    recycleView.adapter = UserAdapter(this, data)

                } else {
                    Log.e("TAG", "Error getting documents.", task.exception)
                }
            }



    }




}
