package br.com.dnsistemas.jetpackcomposerecycleview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dnsistemas.jetpackcomposerecycleview.components.MyDrawerContent
import br.com.dnsistemas.jetpackcomposerecycleview.components.MyFab
import br.com.dnsistemas.jetpackcomposerecycleview.components.MyTopAppBar
import br.com.dnsistemas.jetpackcomposerecycleview.model.NavigationItem
import br.com.dnsistemas.jetpackcomposerecycleview.model.SuperHero
import br.com.dnsistemas.jetpackcomposerecycleview.pages.HomePage
import br.com.dnsistemas.jetpackcomposerecycleview.pages.NotificationPage
import br.com.dnsistemas.jetpackcomposerecycleview.pages.SettiingPage
import br.com.dnsistemas.jetpackcomposerecycleview.ui.theme.JetpackComposeRecycleViewTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeRecycleViewTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val drawerHostState = rememberDrawerState(DrawerValue.Closed)
                val coroutineScope = rememberCoroutineScope()
                var selectedIndex by remember { mutableStateOf(0) }
                val navItemList = listOf(
                    NavigationItem("Home", Icons.Default.Home, Icons.Default.Home, 0),
                    NavigationItem(
                        "Notification",
                        Icons.Default.Notifications,
                        Icons.Default.Notifications,
                        5
                    ),
                    NavigationItem("Settings", Icons.Default.Settings, Icons.Default.Settings, 0)
                )
                //ModalNavigationDrawer envolver todo o conteúdo da tela princial ou da tela que ele estiver
                ModalNavigationDrawer(
                    drawerState = drawerHostState,
                    drawerContent = {
                        MyDrawerContent(
                            drawerHostState,
                            coroutineScope
                        )
                    },
                    content = {
                        Scaffold(
                            topBar = {
                                MyTopAppBar(
                                    //Chamada da Snackbar
                                    onClickIcon = { message ->
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(message)
                                        }
                                    },
                                    //Chamada do Drawer
                                    onClickDrawer = {
                                        coroutineScope.launch {
                                            drawerHostState.open()
                                        }
                                    }
                                )
                            },
                            snackbarHost = { SnackbarHost(snackbarHostState) },
                            floatingActionButton = { MyFab() },
                            floatingActionButtonPosition = FabPosition.End,
                            bottomBar = {
                                NavigationBar {
                                    navItemList.forEachIndexed { index, navItem ->
                                        NavigationBarItem(
                                            selected = selectedIndex == index,
                                            onClick = { selectedIndex = index },
                                            label = { Text(text = navItem.title) },
                                            icon = {
                                                BadgedBox(badge = {
                                                    if (navItem.badgeCount > 0)
                                                        Badge() {
                                                            Text(text = navItem.badgeCount.toString())
                                                        }
                                                }) {
                                                    Icon(
                                                        imageVector = if (index == 0) navItem.selectedIcon else navItem.unselectedIcon,
                                                        contentDescription = navItem.title
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        ) { innerPadding ->
                            //SuperHeroStickyView(innerPadding)
                            ContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    when (selectedIndex) {
        0 -> HomePage()
        1 -> NotificationPage()
        2 -> SettiingPage()
        else -> {
            HomePage()
        }
    }
}



