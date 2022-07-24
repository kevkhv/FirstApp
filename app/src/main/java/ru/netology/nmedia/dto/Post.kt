package ru.netology.nmedia.dto

data class Post (
    val id:Int,
    val likes:Int = 0,
    val countShare:Int = 0,
    val author:String,
    val content:String,
    val published:String,
    val likedByMe:Boolean,
    val video:String? = null
)