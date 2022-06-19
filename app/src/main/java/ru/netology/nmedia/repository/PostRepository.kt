package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    fun get():LiveData<List<Post>>
    fun like(postId:Int)
    fun share(postId:Int)
}