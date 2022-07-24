package ru.netology.nmedia.db

import ru.netology.nmedia.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun shareById(id:Int)
    fun likeById(id: Int)
    fun removeById(id: Int)
}