package com.example.androidexam.ui.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidexam.R

@Composable
fun WelcomeScreen () {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Welcome to Fred's Pub Quiz",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary

        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(R.drawable.pubquizlogo),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* TODO: Start Quiz */ }) {
            Text("Start Quiz")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* TODO: Exit */ }) {
            Text("Exit")
        }
    }
}