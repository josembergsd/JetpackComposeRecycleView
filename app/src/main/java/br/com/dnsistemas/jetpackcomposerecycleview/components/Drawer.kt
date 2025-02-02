package br.com.dnsistemas.jetpackcomposerecycleview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//Conteudo do drawer
@Composable
fun MyDrawerContent(drawerState: DrawerState, scope: CoroutineScope) {

    ModalDrawerSheet {
        Text(text = "Fisrt Action", modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                scope.launch { drawerState.close() }
            }
        )
        Text(text = "Second Action", modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                scope.launch { drawerState.close() }
            }
        )
        Text(text = "Thirth Action", modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                scope.launch { drawerState.close() }
            }
        )
        HorizontalDivider(color = Color.Gray, thickness = 1.dp)
        Text(text = "Fifth Action", modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                scope.launch { drawerState.close() }
            }
        )
        Text(text = "Sixth Action", modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                scope.launch { drawerState.close() }
            }
        )
        Text(text = "Seventh Action", modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                scope.launch { drawerState.close() }
            }
        )
    }
}