package br.com.dnsistemas.jetpackcomposerecycleview

import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeRecycleViewTheme {
                //val scaffoldState = rememberScaffoldState()
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()
                Scaffold(
                    topBar = {
                        MyTopAppBar{ message ->
                            coroutineScope.launch {
                                Log.i("TAG", "onCreate: Pressionado")
                                snackbarHostState.showSnackbar(message)
                            } } },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //SimpleRecycleView(innerPadding)
                    //SuperHeroView(innerPadding)
                    //SuperHeroGridView(innerPadding)
                    //SuperHeroWithSpecialControlView(innerPadding)
                    SuperHeroStickyView(innerPadding)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(onClickIcon: (String) -> Unit) {
    TopAppBar(
        title = { Text("TopAppBar")},
        Modifier.background(Color.Blue),
        colors = TopAppBarColors(
            containerColor = Color.Red,
            titleContentColor = Color.White,
            scrolledContainerColor = Color.Gray,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
            ),
        navigationIcon = {
                IconButton(onClick = { onClickIcon("Retornar") }){
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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

@Composable
fun MySnackBar() {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuperHeroStickyView(innerPadding: PaddingValues) {
    val context = LocalContext.current
    val superhero: Map<String, List<SuperHero>> = getSuperHeroes().groupBy { it.publisher }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 18.dp)) {
        superhero.forEach{ (publisher, mySperHero) ->
            stickyHeader {
                Text(
                    text = publisher,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Blue), fontSize = 16.sp, color = Color.White)
            }
            items(mySperHero) { superhero ->
                ItemHero(superhero = superhero) {
                    Toast.makeText(context, it.realName, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

@Composable
fun SuperHeroWithSpecialControlView(innerPadding: PaddingValues) {
    val context = LocalContext.current
    val rvState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.padding(innerPadding)) {
        LazyColumn(
            state = rvState, verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(getSuperHeroes()) { superhero ->
                ItemHero(superhero = superhero)
                { Toast.makeText(context, it.realName, Toast.LENGTH_LONG).show() }
            }
        }

        val showbutton by remember {
            derivedStateOf { //Exibe o botão quando o primeiro item da lista estiver fora da tela
                rvState.firstVisibleItemIndex > 0
            }
        }

        rvState.firstVisibleItemScrollOffset

        if (showbutton) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        rvState.animateScrollToItem(0)}
                    },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text("Topo")
            }
        }
    }
}

@Composable
fun SuperHeroGridView(innerPadding: PaddingValues) {
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(getSuperHeroes()) { superhero ->
                ItemHero(superhero = superhero) {
                    val toastText = it.superheroName
                    Log.d("Toast", "Item SuperHoroView clicked")
                    Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                }
            }
        },
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    )
    LazyColumn(
        modifier = Modifier.padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

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
            ItemHero(superhero = superhero) {
                val toastText = it.superheroName
                Log.d("Toast", "Item SuperHoroView clicked")
                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun ItemHero(superhero: SuperHero, onItemSelected: (SuperHero) -> Unit) {
    Card(
        border = BorderStroke(2.dp, Color.Red),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemSelected(superhero) }) {
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
                text = superhero.publisher,
                fontSize = 10.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            )
        }
    }
}

fun getSuperHeroes(): List<SuperHero> {
    return listOf(
        SuperHero("Spiderman", "Peter Parker", "Marvel", R.drawable.spiderman),
        SuperHero("Wolverine", "James Howlett", "Marvel", R.drawable.logan),
        SuperHero("Batman", "Bruce Wayne", "DC", R.drawable.batman),
        SuperHero("Thor", "Thor Odison", "Marvel", R.drawable.thor),
        SuperHero("Flash", "Jay Garrick", "DC", R.drawable.flash),
        SuperHero("Green Lantern", "Alan Scott", "DC", R.drawable.green_lantern),
        SuperHero("Wonder Woman", "Princess Diana", "DC", R.drawable.wonder_woman)
    )
}

/*
@Composable
fun ItemHero(superhero: SuperHero, onItemSelected: (SuperHero) -> Unit) {
    Card(
        border = BorderStroke(2.dp, Color.Red),
        modifier = Modifier
            .width(200.dp)
            .clickable {
                Log.d("Click", "ItemHero clicked")
                onItemSelected(superhero)
            }.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
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
*/

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
