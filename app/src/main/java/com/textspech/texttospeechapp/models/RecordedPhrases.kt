package com.textspech.texttospeechapp.models

class RecordedPhrases(

    val id: Int,
    val user: User,
    val phrases: MutableList<String>

) {}