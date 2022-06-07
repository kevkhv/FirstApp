package ru.netology.nmedia

data class Post (
    val id:Int,
    var likes:Int,
    var countShare:Int,
    val author:String,
    val content:String,
    val published:String,
    var likedByMe:Boolean
)