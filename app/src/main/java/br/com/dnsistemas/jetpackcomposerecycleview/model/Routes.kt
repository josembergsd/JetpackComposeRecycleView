package br.com.dnsistemas.jetpackcomposerecycleview.model

sealed class Routes(val route: String, val title: String) {
    object Home: Routes("home", "Home")
    object Notification: Routes("notification", "Notification")
    object Settings: Routes("setting", "Settings")
}