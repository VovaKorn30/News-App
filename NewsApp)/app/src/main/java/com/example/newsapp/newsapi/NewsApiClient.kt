package com.example.newsapp.newsapi

import com.example.newsapp.art.Art
import com.example.newsapp.healthy.Healthy
import com.example.newsapp.newsapi.Contast.APIKEY
import com.example.newsapp.newsapi.Contast.BEGIN_DATE
import com.example.newsapp.newsapi.Contast.END_DATE
import com.example.newsapp.newsapi.Contast.SORT
import com.example.newsapp.newsapi.Contast.TIMEOUT
import com.example.newsapp.politices.Politices
import com.example.newsapp.searchscreen.Search
import com.example.newsapp.sports.Sports
import com.example.newsapp.sundayreview.SundayReview
import com.example.newsapp.technlogy.Technlogy
import com.example.newsapp.worldnews.World
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object NewsApiClient {
    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }

            }
        }

        install(HttpTimeout) {
            connectTimeoutMillis = TIMEOUT
            socketTimeoutMillis = TIMEOUT
            requestTimeoutMillis = TIMEOUT
        }
    }

    suspend fun getAllNews(): News {
        return client.get("https://api.nytimes.com/svc/topstories/v2/home.json?api-key=$APIKEY")
            .body()
    }

    suspend fun Healthy(): Healthy {
        return client.get("https://api.nytimes.com/svc/topstories/v2/health.json?api-key=$APIKEY")
            .body()
    }

    suspend fun Technlogy(): Healthy {
        return client.get("https://api.nytimes.com/svc/topstories/v2/technology.json?api-key=$APIKEY")
            .body()
    }

    suspend fun Politics(): Healthy {
        return client.get("https://api.nytimes.com/svc/topstories/v2/politics.json?api-key=$APIKEY")
            .body()
    }

    suspend fun Art(): Healthy {
        return client.get("https://api.nytimes.com/svc/topstories/v2/arts.json?api-key=$APIKEY")
            .body()
    }

    suspend fun Sports(): Healthy {
        return client.get("https://api.nytimes.com/svc/topstories/v2/sports.json?api-key=$APIKEY")
            .body()
    }

    suspend fun SundayReview(): Healthy {
        return client.get("https://api.nytimes.com/svc/topstories/v2/sundayreview.json?api-key=$APIKEY")
            .body()
    }

    suspend fun WorldNews(): World {
        return client.get("https://api.nytimes.com/svc/topstories/v2/world.json?api-key=$APIKEY")
            .body()
    }

    suspend fun getSearch(query: String): Search {
        return client.get("https://api.nytimes.com/svc/search/v2/articlesearch.json?q=${query}&api-key=${APIKEY}&begin_date=${BEGIN_DATE}&end_date=${END_DATE}&sort=${SORT}&page=1")
            .body()

    }


}
