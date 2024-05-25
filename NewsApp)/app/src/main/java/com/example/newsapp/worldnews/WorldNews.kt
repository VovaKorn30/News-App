package com.example.newsapp.worldnews

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.navigation.Screen
import com.example.newsapp.newsapi.MainViewModels
import com.example.newsapp.newsapi.ResultState
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WorldNewsScreen(navController: NavController) {
    val viewModel: MainViewModels = koinInject()

    var worlddata by remember { mutableStateOf<World?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.getAllWorldNews()
    }

    val worldstate by viewModel.allWorldNews.collectAsState()
    when (worldstate) {
        is ResultState.Error -> {
            val error = (worldstate as ResultState.Error).error
            isLoading = false
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = error.toString(), color = Color.Red)
            }
        }
        is ResultState.Loading -> {
            isLoading = true
        }
        is ResultState.Success -> {
            val success = (worldstate as ResultState.Success).response
            isLoading = false
            worlddata = success
        }
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = "Всі новини",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.ExtraBold
            )
        })
    }) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(top = it.calculateTopPadding(), start = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                worlddata?.let { news ->
                    items(news.results) { home ->
                        WorldNewsItem(result = home, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun WorldNewsItem(result: Result, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    Screen.DetailScreen.route +
                            "/${Uri.encode(result.multimedia[0].url)}/${result.title}/${result.abstract}/${result.itemType}/${result.updatedDate}/${result.section}/${result.byline}"
                )
            }
            .height(400.dp)
            .padding(10.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val image: Resource<Painter> = asyncPainterResource(data = result.multimedia[0].url)
            KamelImage(
                resource = image,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = result.title,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(all = 10.dp)
                    .background(Color.LightGray.copy(alpha = 0.25f)),
                color = Color.White
            )

            Text(
                text = result.byline,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(10.dp)
                    .background(Color.LightGray.copy(alpha = 0.15f)),
                color = Color.White
            )
        }
    }
}
