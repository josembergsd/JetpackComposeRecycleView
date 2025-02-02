package br.com.dnsistemas.jetpackcomposerecycleview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MyFab() {
    FloatingActionButton(onClick = { /*TODO*/ }, containerColor = Color.Blue, contentColor = Color.White ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
    }
}