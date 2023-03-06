package com.example.myapplication.moodle

import com.google.firebase.firestore.DocumentId

data class User(@DocumentId var id:String ,var name:String ,var number:String,var address :String )
