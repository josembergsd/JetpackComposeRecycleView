package br.com.dnsistemas.jetpackcomposerecycleview.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(onClickIcon: (String) -> Unit, onClickDrawer: () -> Unit) {
    TopAppBar(
        title = { Text("TopAppBar") },
        Modifier.background(Color.Blue),
        colors = TopAppBarColors(
            containerColor = Color.Red,
            titleContentColor = Color.White,
            scrolledContainerColor = Color.Gray,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(onClick = { onClickDrawer() } ){
                Icon(imageVector = Icons.AutoMirrored.Filled.MenuBook, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = {onClickIcon("Buscar")}){
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
            IconButton(onClick = {onClickIcon("Perigo")}){
                Icon(imageVector = Icons.Filled.Close, contentDescription = "dangerous")
            }
        }
    )
}