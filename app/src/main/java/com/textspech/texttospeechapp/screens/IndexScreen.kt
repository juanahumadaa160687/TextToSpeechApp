package com.textspech.texttospeechapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.textspech.texttospeechapp.R

@Composable
fun IndexScreen(navController: NavController) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(id= R.color.secondary_color)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ){

            item{
                Image(
                    painter = painterResource(id = R.mipmap.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .width(250.dp)
                        .height(100.dp)
                )

                Spacer(modifier = Modifier.size(64.dp))

                Button(
                    onClick = { navController.navigate("sign-in") },
                    modifier = Modifier.width(200.dp).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id= R.color.background_color))
                ) {
                    Text(text = "Comenzar", style = MaterialTheme.typography.titleMedium, color = colorResource(id= R.color.tertiary_color))

                }
                Spacer(modifier = Modifier.size(64.dp))
            }
        }
    }
}