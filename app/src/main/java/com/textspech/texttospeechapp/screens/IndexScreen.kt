package com.textspech.texttospeechapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.textspech.texttospeechapp.R
import com.textspech.texttospeechapp.ui.theme.gradient_colors

@Composable
fun IndexScreen(navController: NavController) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.Transparent

    ) {innerPadding ->

        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = gradient_colors,
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY,
                        tileMode = TileMode.Clamp
                    )
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .padding(16.dp),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = { navController.navigate("sign-in") },
                modifier = Modifier.width(200.dp).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id= R.color.background_color))
            ) {
                Text(text = "Comenzar", style = MaterialTheme.typography.titleMedium, color = colorResource(id= R.color.tertiary_color))

            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}