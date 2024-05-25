package com.example.newsapp

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.di.appModule
import com.example.newsapp.healthy.Healthy
import com.example.newsapp.navigation.Entry
import com.example.newsapp.navigation.Screen
import com.example.newsapp.newsapi.MainViewModels
import com.example.newsapp.newsapi.News
import com.example.newsapp.newsapi.Result
import com.example.newsapp.newsapi.ResultState
import com.example.newsapp.ui.theme.NewsAppTheme
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.koinInject
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                startKoin {
                    androidContext(this@MainActivity)
                    androidLogger()
                    modules(appModule)
                }
                Entry()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: MainViewModels = koinInject()

    var newsdata by remember { mutableStateOf<News?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isNews by remember { mutableStateOf(false) }
    var healthy by remember { mutableStateOf(false) }
    var Tecnoligy by remember { mutableStateOf(false) }
    var Politices by remember { mutableStateOf(false) }
    var Art by remember { mutableStateOf(false) }
    var Sports by remember { mutableStateOf(false) }
    var SundayReview by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isNews) {
        viewModel.getAllNews()
        if (healthy) {
            viewModel.getAllHealthy()
        }
        if (Tecnoligy) {
            viewModel.getAllTechnlogy()
        }
        if (Politices) {
            viewModel.getAllPolitics()
        }
        if (Art) {
            viewModel.getAllArt()
        }
        if (Sports) {
            viewModel.getAllSports()
        }
        if (SundayReview) {
            viewModel.getAllSundayReview()
        }
    }

    var isLatest by remember { mutableStateOf(false) }
    val state by viewModel.allNews.collectAsState()

    when (state) {
        is ResultState.Loading -> {
            isLatest = true
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ResultState.Success -> {
            val response = (state as ResultState.Success).response
            isLatest = false
            newsdata = response
        }
        is ResultState.Error -> {
            val error = (state as ResultState.Error).error
            isLatest = false
            Text(text = error.toString())
        }
    }

    var helthydata by remember { mutableStateOf<Healthy?>(null) }
    var tecdata by remember { mutableStateOf<Healthy?>(null) }
    var politicesdata by remember { mutableStateOf<Healthy?>(null) }
    var artdata by remember { mutableStateOf<Healthy?>(null) }
    var sportsdata by remember { mutableStateOf<Healthy?>(null) }
    var sundaydata by remember { mutableStateOf<Healthy?>(null) }
    var isCategory by remember { mutableStateOf(false) }

    val healthyState by viewModel.allHealthy.collectAsState()
    when (healthyState) {
        is ResultState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ResultState.Success -> {
            val response = (healthyState as ResultState.Success).response
            helthydata = response
        }
        is ResultState.Error -> {
            val error = (healthyState as ResultState.Error).error
            Text(text = error.toString())
        }
    }

    val sundayState by viewModel.allSundayReview.collectAsState()
    when (sundayState) {
        is ResultState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ResultState.Success -> {
            val response = (sundayState as ResultState.Success).response
            helthydata = response
        }
        is ResultState.Error -> {
            val error = (sundayState as ResultState.Error).error
            Text(text = error.toString())
        }
    }

    val tecState by viewModel.allTechnlogy.collectAsState()
    when (tecState) {
        is ResultState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ResultState.Success -> {
            val response = (tecState as ResultState.Success).response
            helthydata = response
        }
        is ResultState.Error -> {
            val error = (tecState as ResultState.Error).error
            Text(text = error.toString())
        }
    }

    val politicsState by viewModel.allPolitics.collectAsState()
    when (politicsState) {
        is ResultState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ResultState.Success -> {
            val response = (politicsState as ResultState.Success).response
            helthydata = response
        }
        is ResultState.Error -> {
            val error = (politicsState as ResultState.Error).error
            Text(text = error.toString())
        }
    }

    val artState by viewModel.allArt.collectAsState()
    when (artState) {
        is ResultState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ResultState.Success -> {
            val response = (artState as ResultState.Success).response
            helthydata = response
        }
        is ResultState.Error -> {
            val error = (artState as ResultState.Error).error
            Text(text = error.toString())
        }
    }

    val sportsState by viewModel.allSports.collectAsState()
    when (sportsState) {
        is ResultState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ResultState.Success -> {
            val response = (sportsState as ResultState.Success).response
            helthydata = response
        }
        is ResultState.Error -> {
            val error = (sportsState as ResultState.Error).error
            Text(text = error.toString())
        }
    }

    Scaffold(topBar = {
        SearchBar(
            query = searchQuery,
            onQueryChange = { newQuery -> searchQuery = newQuery },
            onSearch = { isNews = true },
        )
    }) { it ->

        if (isLatest) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Останні новини",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 7.dp, start = 14.dp, bottom = 6.dp)
                )

            }
            LazyRow(
                modifier = Modifier
                    .width(396.dp)
                    .height(240.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val filteredNews = newsdata?.results?.filter {
                    it.title.contains(searchQuery, ignoreCase = true)
                }
                filteredNews?.let { fav ->
                    items(fav) { home ->
                        HomeNews(result = home, navController = navController)
                    }
                }
            }

            LazyRow(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                item {
                    Button(
                        onClick = {
                            isCategory = true
                            healthy = true
                            viewModel.getAllHealthy()
                        },
                        colors = ButtonDefaults.buttonColors(Color(0XFF0096fa)),
                    ) {
                        Text(text = "Здоров'я")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            isCategory = true
                            Tecnoligy = true
                            viewModel.getAllTechnlogy()
                        },
                        colors = ButtonDefaults.buttonColors(Color(0XFF0096fa)),
                    ) {
                        Text(text = "Технології")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            isCategory = true
                            Politices = true
                            viewModel.getAllPolitics()
                        },
                        colors = ButtonDefaults.buttonColors(Color(0XFF0096fa)),
                    ) {
                        Text(text = "Політика")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            isCategory = true
                            Art = true
                            viewModel.getAllArt()
                        },
                        colors = ButtonDefaults.buttonColors(Color(0XFF0096fa)),
                    ) {
                        Text(text = "Мистецтво")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            isCategory = true
                            Sports = true
                            viewModel.getAllSports()
                        },
                        colors = ButtonDefaults.buttonColors(Color(0XFF0096fa)),
                    ) {
                        Text(text = "Спорт")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            isCategory = true
                            SundayReview = true
                            viewModel.getAllSundayReview()
                        },
                        colors = ButtonDefaults.buttonColors(Color(0XFF0096fa)),
                    ) {
                        Text(text = "Огляд новин, що відбулися минулої неділі")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isCategory) {
                    helthydata?.results?.let { result ->
                        items(result) {
                            Catagery1(result = it, navController)
                        }
                    }
                } else {
                    val filteredNews = newsdata?.results?.filter {
                        it.title.contains(searchQuery, ignoreCase = true)
                    }
                    filteredNews?.let { result ->
                        items(result) {
                            HomeNews(result = it, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, onSearch: () -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        leadingIcon = {
            IconButton(onClick = onSearch) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        }
    )
}

@Composable
fun Catagery1(
    result: com.example.newsapp.healthy.Result,
    navController: NavController,
) {
    Card(
        modifier = Modifier
            .width(345.dp)
            .clickable {
                navController.navigate(
                    Screen.DetailScreen.route +
                            "/${Uri.encode(result.multimedia?.first()?.url.toString())}/${result.title}/${result.abstract}/${result.itemType}/${result.updatedDate}/${result.createdDate}/${result.byline}"
                )
            }
            .height(228.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            val image: Resource<Painter> = asyncPainterResource(data = result.multimedia?.first()?.url.toString())
            KamelImage(
                resource = image,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray.copy(alpha = 0.35f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.title,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 7.dp, start = 6.dp, end = 6.dp),
                    color = Color.White,
                )
            }
            Text(
                text = result.byline,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                color = Color.White,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun HomeNews(result: Result, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable {
                navController.navigate(
                    Screen.DetailScreen.route +
                            "/${Uri.encode(result.multimedia?.first()?.url)}/${result.title}/${result.abstract}/${result.itemType}/${result.updatedDate}/${result.createdDate}/${result.byline}"
                )
            }
            .width(396.dp)
            .height(240.dp)
            .padding(start = 9.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            val image: Resource<Painter> = asyncPainterResource(data = result.multimedia?.first()?.url.toString())
            KamelImage(
                resource = image,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray.copy(alpha = 0.35f))
            ) {
                Text(
                    text = result.title,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp, start = 3.dp, end = 3.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = result.byline,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
