package ru.netology.nmedia

data class Post (
    val id:Int,
    val likes:Int,
    val countShare:Int,
    val author:String,
    val content:String,
    val published:String,
    val likedByMe:Boolean
)