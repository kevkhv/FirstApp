package ru.netology.nmedia.repository

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post
import kotlin.properties.Delegates

class PostRepositoryFileImpl(private val context: Context) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)
    private val prefs = context.getSharedPreferences("repo",Context.MODE_PRIVATE)
    private var nextId:Int by Delegates.observable(
        prefs.getInt(NEXT_ID_PREFS_KEY,1)
    ) { _, _, newValue ->
        prefs.edit { putInt(NEXT_ID_PREFS_KEY,newValue) }
    }

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {
            sync()
        }
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    override fun get(): LiveData<List<Post>> = data

    override fun like(postId: Int) {
        posts = posts.map {
            if (it.id != postId) it
            else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
        sync()
    }

    override fun share(postId: Int) {
        posts = posts.map {
            if (it.id != postId) it
            else it.copy(countShare = it.countShare + 1)
        }
        data.value = posts
        sync()
    }

    override fun removeByID(postId: Int) {
        posts = posts.filter { it.id != postId }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        posts = if (post.id == 0) {
            listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
        } else posts.map { if (it.id != post.id) it else it.copy(content = post.content) }
        data.value = posts
        sync()
        return
    }

    private companion object {
        const val NEXT_ID_PREFS_KEY = "nextId"
    }
}