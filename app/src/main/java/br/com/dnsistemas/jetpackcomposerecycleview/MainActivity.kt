package br.com.dnsistemas.jetpackcomposerecycleview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.dnsistemas.jetpackcomposerecycleview.components.MyDrawerContent
import br.com.dnsistemas.jetpackcomposerecycleview.components.MyFab
import br.com.dnsistemas.jetpackcomposerecycleview.components.MyTopAppBar
import br.com.dnsistemas.jetpackcomposerecycleview.model.NavigationItem
import br.com.dnsistemas.jetpackcomposerecycleview.model.Routes
import br.com.dnsistemas.jetpackcomposerecycleview.screens.HomePage
import br.com.dnsistemas.jetpackcomposerecycleview.screens.LoginPage
import br.com.dnsistemas.jetpackcomposerecycleview.screens.NotificationPage
import br.com.dnsistemas.jetpackcomposerecycleview.screens.SettiingPage
import br.com.dnsistemas.jetpackcomposerecycleview.ui.theme.JetpackComposeRecycleViewTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeRecycleViewTheme {
                val navController = rememberNavController()
                MyScreenWithDrawer(navController)
            }
        }
    }
}

@Composable
fun MyNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "home", modifier = modifier) { //Rota da tela inicial
        composable(Routes.Home.route) { HomePage() }  //Usa as rotas da selead class Routes
        composable("login") { LoginPage() }
        composable(Routes.Notification.route) { NotificationPage() }
        composable(Routes.Settings.route) { SettiingPage() }
    }
}


@Composable
fun MyScreenWithDrawer(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerHostState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navItemList = listOf(
        NavigationItem("Home", Icons.Default.Home, Icons.Default.Home, 0),
        NavigationItem("Notification", Icons.Default.Notifications, Icons.Default.Notifications, 5),
        NavigationItem("Settings", Icons.Default.Settings, Icons.Default.Settings, 0)
    )
    //Lista das rotas
    val screens = listOf(
        Routes.Home,
        Routes.Notification,
        Routes.Settings
    )
    //ModalNavigationDrawer envolver todo o conteúdo da tela princial
    ModalNavigationDrawer(
        drawerState = drawerHostState,
        drawerContent = {
            MyDrawerContent(
                drawerHostState,
                coroutineScope,
                navController
            )
        },
        gesturesEnabled = true,
        scrimColor = Color.Red, //Põe uma cor no fundo da tela quando o drawer é spandido cobringo o conteúdo
    ){
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
                    //navItemList.forEachIndexed { index, navItem ->
                    screens.forEachIndexed { index, screen ->
                        NavigationBarItem(
//                                selected = selectedIndex == index,
//                                onClick = { selectedIndex = index },
                            selected = navController
                                .currentBackStackEntryAsState()
                                .value?.destination?.route == screen.route,  //Verifica se a tela atual corresponde à tela do item
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph //Remove telas da pilha de navegação
                                        .findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true //Lança somente uma instãncia da tela
                                    restoreState = true //Restaura o estado da tela anterior ao voltar
                                }
                            },
                            label = { Text(text = screen.title) },
                            icon = {
                                BadgedBox(badge = {
                                    if (navItemList[index].badgeCount > 0)
                                        Badge {
                                            Text(text = navItemList[index].badgeCount.toString())
                                        }
                                }) {
                                    Icon(
                                        imageVector = if (index == 0)
                                            navItemList[index].selectedIcon else
                                                navItemList[index].unselectedIcon,
                                        contentDescription = navItemList[index].title
                                    )
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            //SuperHeroStickyView(innerPadding)
            //ContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex)
            MyNavHost(navController, Modifier.padding(innerPadding)) // NavHost dentro do Scaffold
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeRecycleViewTheme  {
        val navController = rememberNavController()
        MyScreenWithDrawer(navController)
    }
}

