package ru.netology.nmedia.vewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.SQLiteRepository

private val empty = Post(
    id = 0,
    likes = 0,
    countShare = 0,
    author = "Me",
    content = "",
    published = "01 января в 00:00",
    likedByMe = false
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = SQLiteRepository(dao = AppDb.getInstance(context = application).postDao)
    val data = repository.get()
    val edited = MutableLiveData(empty)

    fun like(postId: Int) = repository.like(postId)

    fun share(post: Post) {
        repository.share(post.id)
    }

    fun removeByID(postId: Int) = repository.removeByID(postId)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            edited.value = it.copy(content = text)
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }
}