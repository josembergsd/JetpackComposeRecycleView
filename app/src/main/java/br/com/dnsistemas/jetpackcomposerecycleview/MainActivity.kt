package br.com.dnsistemas.jetpackcomposerecycleview

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dnsistemas.jetpackcomposerecycleview.model.SuperHero
import br.com.dnsistemas.jetpackcomposerecycleview.ui.theme.JetpackComposeRecycleViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeRecycleViewTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //SimpleRecycleView(innerPadding)
                    SuperHeroView(innerPadding)
                }
            }
        }
    }
}

@Composable
fun SuperHeroView(innerPadding: PaddingValues) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(getSuperHeroes()) { superhero ->
            ItemHero(superhero = superhero, context = context) {
                val toastText = it.superheroName
                if (toastText.isNotEmpty()) {
                    Log.d("Toast", "Toast triggered")
                    Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                } else {
                    Log.e("Toast", "Toast text is empty")
                }
            }
        }
    }
}

@Composable
fun ItemHero(superhero: SuperHero, context: Context, onItemSelected: (SuperHero) -> Unit) {
    Card(
        border = BorderStroke(2.dp, Color.Red),
        modifier = Modifier
            .width(200.dp)
            .clickable {
                Log.d("Click", "Item clicked")
                onItemSelected(superhero)
            }
    ) {
        Column {
            Image(
                painter = painterResource(id = superhero.photo),
                contentDescription = "SuperHero Avatar",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = superhero.superheroName,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = superhero.realName,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 12.sp
            )
            Text(
                text = superhero.publicher,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp),
                fontSize = 10.sp
            )
        }
    }
}

fun getSuperHeroes(): List<SuperHero> {
    return listOf(
        SuperHero("Spiderman", "Peter Parker", "Marvel", R.drawable.spiderman),
        SuperHero("Wolverine", "Logan", "Marvel", R.drawable.logan),
        SuperHero("Batman", "Bruce Wayne", "DC", R.drawable.batman),
        SuperHero("Thor", "Thor Odinson", "Marvel", R.drawable.thor),
        SuperHero("Flash", "Bary Allen", "DC", R.drawable.flash),
        SuperHero("Green Lantern", "Hall Jordan", "DC", R.drawable.green_lantern),
        SuperHero("Wonder Woman", "Princess Diana", "DC", R.drawable.wonder_woman),
    )
}

/*
@Composable
fun SuperHeroView(innerPadding: PaddingValues) {
    val context = LocalContext.current
    //LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    LazyColumn(
        modifier = Modifier.padding(innerPadding),
        verticalArrangement = Arrangement
            .spacedBy(8.dp)
    ) {
        items(getSuperHeroes()) { superhero ->
            ItemHero(
                superhero = superhero, context = context
            ) {
                val toastText = it.superheroName
                if(toastText.isNotEmpty()) {
                    Log.i("Toast", "Toast generated")
                    Toast.makeText(
                        context,
                        toastText,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e("Toast", "Toast test is empty")
                }
            }
        }
    }
}

@Composable
fun ItemHero(superhero: SuperHero, onItemSelected: (SuperHero) -> Unit, context: Context) {
    Card(
        border = BorderStroke(2.dp, Color.Red),
        modifier = Modifier
            .width(200.dp)
            .clickable {
                Log.d("Click", "Item clicked")
                onItemSelected(superhero)
            },
    ) {
        Column() {
            Image(
                painter = painterResource(id = superhero.photo),
                contentDescription = "SuperHero Avatar",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = superhero.superheroName,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = superhero.realName,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 12.sp
            )
            Text(
                text = superhero.publicher,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp),
                fontSize = 10.sp
            )
        }
    }
}

fun getSuperHeroes(): List<SuperHero> {
    return listOf(
        SuperHero("Spiderman", "Peter Parker", "Marvel", R.drawable.spiderman),
        SuperHero("Wolverine", "Logan", "Marvel", R.drawable.logan),
        SuperHero("Batman", "Bruce Wayne", "DC", R.drawable.batman),
        SuperHero("Thor", "Thor Odinson", "Marvel", R.drawable.thor),
        SuperHero("Flash", "Bary Allen", "DC", R.drawable.flash),
        SuperHero("Green Lantern", "Hall Jordan", "DC", R.drawable.green_lantern),
        SuperHero("Wonder Woman", "Princess Diana", "DC", R.drawable.wonder_woman),
    )
}
*/

@Composable
fun SimpleRecycleView(innerPadding: PaddingValues) {
    val myList = listOf("Josemberg", "Luciana", "Ian Lucas", "Analú")
    LazyColumn {
        item { Text(text = "Header") }
//        items(4) {
//            Text(text = "Item $it")
//        }
        items(myList) {
            Text(text = "Olá $myList")
        }
        item { Text(text = "Footer") }
    }
}
