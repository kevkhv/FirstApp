package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun get():LiveData<List<Post>>
    fun like(postId:Int)
    fun share(postId:Int)
    fun removeByID(postId:Int)
    fun save(post: Post)
}