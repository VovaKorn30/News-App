package com.example.newsapp.searchscreen


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Multimedia(
    @SerialName("caption")
    val caption: String?=null,
    @SerialName("credit")
    val credit: String?=null,
    @SerialName("crop_name")
    val cropName: String,
    @SerialName("height")
    val height: Int,
    @SerialName("legacy")
    val legacy: Legacy?=null,
    @SerialName("rank")
    val rank: Int,
    @SerialName("subType")
    val subType: String,
    @SerialName("subtype")
    val subtype: String,
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String,
    @SerialName("width")
    val width: Int
)