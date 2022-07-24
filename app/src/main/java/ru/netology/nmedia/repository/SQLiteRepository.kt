package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.dto.Post

class SQLiteRepository(private val dao: PostDao) : PostRepository {

    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun get(): LiveData<List<Post>> = data

    override fun like(postId: Int) {
        dao.likeById(postId)
        posts = posts.map {
            if (it.id != postId) it
            else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun share(postId: Int) {
        dao.shareById(postId)
        posts = posts.map {
            if (it.id != postId) it
            else it.copy(countShare = it.countShare + 1)
        }
        data.value = posts
    }

    override fun removeByID(postId: Int) {
        dao.removeById(postId)
        posts = posts.filter { it.id != postId }
        data.value = posts
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }
}