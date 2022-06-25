package ru.netology.nmedia.vewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0,
    likes = 0,
    countShare = 0,
    author = "",
    content = "",
    published = "01 января в 00:00",
    likedByMe = false
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    val edited = MutableLiveData(empty)

    fun like(postId: Int) = repository.like(postId)
    fun share(postId: Int) = repository.share(postId)
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