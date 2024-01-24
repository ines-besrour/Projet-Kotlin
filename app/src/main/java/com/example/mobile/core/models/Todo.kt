package com.example.mobile.core.models

import java.util.Date

data class Todo(
    val id : Int,
    val name : String,
    val description : String,
    val statut : String,
    val createdAt : Date,
)
