package com.textspech.texttospeechapp.models

class User(

    val id: Int,
    val firstname: String,
    val lastname: String,
    val email: String,
    var password: String,
    val phone: String,

    ) {
    override fun toString(): String {
        return "User(id=$id, firstname='$firstname', lastname='$lastname', email='$email')"
    }

}