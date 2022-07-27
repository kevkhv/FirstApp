package ru.netology.nmedia.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity(tableName = "posts")
class PostEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    val likes: Int = 0,
    val countShare: Int = 0,
    val author: String,
    val content: String,
    val published: String,

    @ColumnInfo(name = "likedByMe")
    val likedByMe: Boolean,
    val video: String? = null
) {

    companion object {
        internal fun PostEntity.toModel() = Post(
            id = id,
            author = author,
            content = content,
            published = published,
            likedByMe = likedByMe,
            likes = likes,
            countShare = countShare,
            video = video
        )

        internal fun Post.toEntity() = PostEntity(
            id = id,
            author = author,
            content = content,
            published = published,
            likedByMe = likedByMe,
            likes = likes,
            countShare = countShare,
            video = video
        )
    }
}