package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.UserItemBinding
import com.example.myapplication.moodle.User
import com.google.firebase.firestore.FirebaseFirestore

class UserAdapter(var context: Context, var data: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val db = FirebaseFirestore.getInstance()

    class UserViewHolder(var binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return UserViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.setBackground(
            ContextCompat.getDrawable(
                holder.itemView.getContext(),
                R.drawable.underline
            )
        );

        val user: User = data[position]
        holder.binding.txtName.text = user.name
        holder.binding.txtAddress.text = user.address
        holder.binding.txtNumber.text = user.number
        holder.binding.btnDelete.setOnClickListener {

            val docRef = db.collection("Users").document(data[position].id)
            docRef.delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "delete is Success", Toast.LENGTH_SHORT).show()
                    data.removeAt(position)
                    notifyDataSetChanged()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "delete is Failure", Toast.LENGTH_SHORT).show()
                }
        }


    }

}