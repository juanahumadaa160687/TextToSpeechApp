package com.textspech.texttospeechapp.data

import com.textspech.texttospeechapp.models.User

val users = mutableListOf(
    User(
        id = 1,
        firstname = "John",
        lastname = "Doe",
        email = "john.doe@example.com",
        password = "password123",
        phone = "123-456-7890"
    ),
    User(
        id = 2,
        firstname = "Jane",
        lastname = "Smith",
        email = "jane.smith@example.com",
        password = "password456",
        phone = "",
    ),
    User(
        id = 3,
        firstname = "Bob",
        lastname = "Johnson",
        email = "bob.johnson@example.com",
        password = "password789",
        phone = "978-456-7890",
    ),
    User(
        id = 4,
        firstname = "Charlie",
        lastname = "Brown",
        email = "charlie.brown@example.com",
        password = "password101",
        phone = "106-456-7890",
    ),
    User(
        id = 5,
        firstname = "Alice",
        lastname = "Williams",
        email = "alice.williams@example.com",
        password = "password202",
        phone = "111-456-7890"
    )

)