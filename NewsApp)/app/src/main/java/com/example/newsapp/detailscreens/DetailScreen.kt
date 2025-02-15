package com.example.newsapp.detailscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import coil.compose.AsyncImage
import com.example.newsapp.newsapi.MainViewModels
import com.example.newsapp.newsapi.Repository
import com.example.newsapp.roomdatabase.DataBase
import com.example.newsapp.roomdatabase.FavItem

@Composable
fun HomeDetailScreen(
    navController: NavController,
    image: String?,
    tittle: String?,
    des: String?,
    newdes: String?,
    date: String?,
    about: String?,
    write: String?
) {
    val context= LocalContext.current
    val db= Room.databaseBuilder(
        context,
        DataBase::class.java,
        "demo.db"
    ).allowMainThreadQueries().build()
    val repository = remember {
        Repository(db)
    }
    val viewModel = remember {
        MainViewModels(repository)
    }
    var fav by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            AsyncImage(
                model = image,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(399.dp),
                contentScale = ContentScale.Crop
            )
            val scrollState = rememberScrollState()
            Card(
                modifier = Modifier
                    .padding(top = 314.dp)
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .height(400.dp)
                    .align(Alignment.Center),
                colors = CardDefaults.cardColors(Color.White),
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Text(
                        text = "$tittle",
                        fontSize = 24.sp // Збільшуємо розмір шрифту на 24sp
                    )
                    Text(
                        text = "$des",
                        modifier = Modifier.padding(vertical = 8.dp),
                        fontSize = 18.sp // Збільшуємо розмір шрифту на 18sp
                    )
                    Text(
                        text = "$newdes",
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 18.sp // Збільшуємо розмір шрифту на 18sp
                    )
                }
                Box(
                    modifier = Modifier
                        .clickable { }
                        .padding(start = 320.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(alpha = 0.40f))
                        .width(56.dp)
                        .height(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (fav) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "",
                            modifier = Modifier
                                .clickable { fav = !fav }
                                .width(40.dp)
                                .height(40.dp)
                                .align(Alignment.Center),
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "",
                            modifier = Modifier
                                .clickable {
                                    fav = !fav
                                    val favt= FavItem(null, image!!, tittle!!,des!!)

                                    favt.let { viewModel.getAllInsert(it) }
                                }
                                .width(40.dp)
                                .height(40.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .width(311.dp)
                    .height(141.dp)
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(Color.LightGray.copy(alpha = 0.90f))
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = "$date",
                        fontSize = 20.sp // Збільшуємо розмір шрифту на 20sp
                    )
                    Text(
                        text = "$about",
                        fontSize = 27.sp // Збільшуємо розмір шрифту на 18sp
                    )
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(1.dp))
            .padding(start = 10.dp, top = 8.dp)
            .width(32.dp)
            .height(32.dp)
            .clickable { navController.popBackStack() }
            .background(Color(0XFFF5F5F5).copy(alpha = 0.90f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = Icons.Outlined.ArrowBackIosNew, contentDescription = "")
    }
}
