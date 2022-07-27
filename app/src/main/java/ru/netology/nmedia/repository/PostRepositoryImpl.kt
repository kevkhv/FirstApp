package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity.Companion.toEntity
import ru.netology.nmedia.entity.PostEntity.Companion.toModel

class PostRepositoryImpl(private val dao: PostDao) : PostRepository {

    override fun get(): LiveData<List<Post>> = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun like(postId: Int) = dao.likeById(postId)

    override fun share(postId: Int) = dao.shareById(postId)

    override fun save(post: Post) = dao.save(post.toEntity())

    override fun removeByID(postId: Int) = dao.removeById(postId)

}